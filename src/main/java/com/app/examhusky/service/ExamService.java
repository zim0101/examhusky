package com.app.examhusky.service;

import com.app.examhusky.dto.EmailDto;
import com.app.examhusky.model.*;
import com.app.examhusky.model.enums.ExamState;
import com.app.examhusky.repository.*;
import com.app.examhusky.service.rabbitmq.publisher.RabbitMQPublisher;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class ExamService {

    private static final Logger log = LoggerFactory.getLogger(ExamService.class);
    private final AuthUserService authUserService;
    private final ExamRepository examRepository;
    private final ExaminerRepository examinerRepository;
    private final CandidateRepository candidateRepository;
    private final QuestionRepository questionRepository;
    private final CandidateExamAnswerRecordService candidateExamAnswerRecordService;
    private final CandidateExamResultService candidateExamResultService;
    private final SortingAndPaginationService sortingAndPaginationService;
    private final RabbitMQPublisher rabbitMQPublisher;

    public ExamService(AuthUserService authUserService,
                       ExamRepository examRepository,
                       ExaminerRepository examinerRepository,
                       CandidateRepository candidateRepository,
                       QuestionRepository questionRepository,
                       CandidateExamAnswerRecordService candidateExamAnswerRecordService,
                       CandidateExamResultService candidateExamResultService,
                       SortingAndPaginationService sortingAndPaginationService,
                       RabbitMQPublisher rabbitMQPublisher) {
        this.authUserService = authUserService;
        this.examRepository = examRepository;
        this.examinerRepository = examinerRepository;
        this.candidateRepository = candidateRepository;
        this.questionRepository = questionRepository;
        this.candidateExamAnswerRecordService = candidateExamAnswerRecordService;
        this.candidateExamResultService = candidateExamResultService;
        this.sortingAndPaginationService = sortingAndPaginationService;
        this.rabbitMQPublisher = rabbitMQPublisher;
    }

    public Exam findById(Integer id) {
        return examRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Exam not found!"));
    }

    public Page<Exam> sortAndPaginateAllActiveExams(HttpSession session,
                                          Optional<Integer> page,
                                          Optional<Integer> size,
                                          Optional<String> sortField,
                                          Optional<String> orderBy) {
        sortingAndPaginationService.removePageAndSortingDataFromSessionOnReload(
                session, page, size, sortField, orderBy);

        Pageable pageable = sortingAndPaginationService.buildPageable(
                sortingAndPaginationService.getPageFromSession(session, page),
                size.orElse(null),
                sortingAndPaginationService.getSortingFieldFromSession(session, sortField),
                sortingAndPaginationService.getOrderByFromSession(session, orderBy)
        );
        if (sortField.isPresent() && orderBy.isPresent()) {
            return examRepository.findByDeletedFalse(pageable);
        } else {
            return examRepository.findByDeletedFalseOrderByStartDateDesc(pageable);
        }
    }

    public Page<Exam> sortAndPaginateAllExamsOfCandidate(HttpSession session,
                                          Optional<Integer> page,
                                          Optional<Integer> size,
                                          Optional<String> sortField,
                                          Optional<String> orderBy) {
        sortingAndPaginationService.removePageAndSortingDataFromSessionOnReload(
                session, page, size, sortField, orderBy);

        Pageable pageable = sortingAndPaginationService.buildPageable(
                sortingAndPaginationService.getPageFromSession(session, page),
                size.orElse(null),
                sortingAndPaginationService.getSortingFieldFromSession(session, sortField),
                sortingAndPaginationService.getOrderByFromSession(session, orderBy)
        );

        Candidate candidate =
                candidateRepository.findByAccount(authUserService.currentAuthAccount()).orElseThrow(() ->
                        new EntityNotFoundException("Candidate not found"));
        if (sortField.isPresent() && orderBy.isPresent()) {
            return examRepository.findByCandidates_Id(candidate.getId(), pageable);
        } else {
            return examRepository.findByCandidates_IdOrderByStartDateDesc(candidate.getId(), pageable);
        }
    }

    public void createOrUpdate(Exam exam) {
        examRepository.save(exam);
        if (exam.getState() == ExamState.PUBLISHED) {
            sendExamUpdateNotificationToAll(exam);
        }
    }

    @Transactional
    public void publish(Exam exam) {
        exam.setState(ExamState.PUBLISHED);
        examRepository.save(exam);
        sendExamPublishNotificationToAll(exam);
    }

    public void start(Exam exam) {
        exam.setState(ExamState.ON_GOING);
        exam.setStartDate(LocalDate.now());
        examRepository.save(exam);
    }

    public void stop(Exam exam) {
        exam.setState(ExamState.ENDED);
        examRepository.save(exam);
    }

    public void softDelete(Exam exam) {
        exam.setDeleted(Boolean.TRUE);
        examRepository.save(exam);
    }

    @Transactional
    public void addExaminerToExam(Exam exam, Examiner examiner) {
        examiner.getExams().add(exam);
        exam.getExaminers().add(examiner);
        examinerRepository.save(examiner);
        examRepository.save(exam);

        sendExamInvitationEmail(examiner.getAccount(), exam);
    }

    @Transactional
    public void removeExaminerFromExam(Exam exam, Examiner examiner) {
        if (exam.getExaminers().size() > 1 &&
                (exam.getState().equals(ExamState.PENDING) ||
                exam.getState().equals(ExamState.PUBLISHED) ||
                exam.getState().equals(ExamState.ON_GOING))) {

            examiner.getExams().remove(exam);
            exam.getExaminers().remove(examiner);
            examinerRepository.save(examiner);
            examRepository.save(exam);

            sendExamInvitationCancelEmail(examiner.getAccount(), exam);
        }
    }

    @Transactional
    public void addCandidateToExam(Candidate candidate, Exam exam) {
        if (exam.getState().equals(ExamState.PENDING) || exam.getState().equals(ExamState.PUBLISHED)) {

            candidate.getExams().add(exam);
            exam.getCandidates().add(candidate);
            candidateRepository.save(candidate);
            examRepository.save(exam);

            if (exam.getState().equals(ExamState.PUBLISHED)) {
                sendExamInvitationEmail(candidate.getAccount(), exam);
            }
        }
    }

    @Transactional
    public void removeCandidateFromExam(Candidate candidate, Exam exam) {
        if (exam.getState().equals(ExamState.PENDING) || exam.getState().equals(ExamState.PUBLISHED)) {

            candidate.getExams().remove(exam);
            exam.getCandidates().remove(candidate);
            candidateRepository.save(candidate);
            examRepository.save(exam);

            if (exam.getState().equals(ExamState.PUBLISHED)) {
                sendExamInvitationCancelEmail(candidate.getAccount(), exam);
            }
        }
    }

    @Transactional
    public void addQuestionToExam(Exam exam, Question question) {
        if (exam.getState().equals(ExamState.PENDING) || exam.getState().equals(ExamState.PUBLISHED)) {
            question.getExams().add(exam);
            exam.getQuestions().add(question);
            questionRepository.save(question);
            examRepository.save(exam);
        }
    }

    @Transactional
    public void removeQuestionFromExam(Question question, Exam exam) {
        if (exam.getState().equals(ExamState.PENDING) || exam.getState().equals(ExamState.PUBLISHED)) {
            question.getExams().remove(exam);
            exam.getQuestions().remove(question);
            questionRepository.save(question);
            examRepository.save(exam);
        }
    }

    @Transactional
    public void prepareEmptyExamPaperAndInitResult(Integer id) {
        Exam exam = findById(id);
        candidateExamAnswerRecordService.initExamPaperForAuthenticatedCandidate(exam);
        candidateExamResultService.initExamResultForAuthenticatedCandidate(exam);
    }

    public void sendExamUpdateNotificationToAll(Exam exam) {
        exam.getExaminers().forEach(examiner -> buildAndSendExamUpdateEmailToQueue(examiner.getAccount(), exam));
        exam.getCandidates().forEach(candidate -> buildAndSendExamUpdateEmailToQueue(candidate.getAccount(), exam));
    }

    public void buildAndSendExamUpdateEmailToQueue(Account account, Exam exam) {
        EmailDto emailDto = new EmailDto();
        emailDto.setMailTo(account.getEmail());
        emailDto.setMailSubject("Exam has been updated!");
        emailDto.setContentType("text/plain; charset=\"utf-8\"");
        emailDto.setMailContent(exam.getTitle() + "  has been updated.");

        rabbitMQPublisher.sendExamUpdateEmail(emailDto);
    }

    public void sendExamPublishNotificationToAll(Exam exam) {
        exam.getExaminers().forEach(examiner -> buildAndSendExamPublishEmailToQueue(examiner.getAccount(), exam));
        exam.getCandidates().forEach(candidate -> buildAndSendExamPublishEmailToQueue(candidate.getAccount(), exam));
    }

    public void buildAndSendExamPublishEmailToQueue(Account account, Exam exam) {
        EmailDto emailDto = new EmailDto();
        emailDto.setMailTo(account.getEmail());
        emailDto.setMailSubject("Exam has been published!");
        emailDto.setContentType("text/plain; charset=\"utf-8\"");
        emailDto.setMailContent(exam.getTitle() + "  has been published.");

        rabbitMQPublisher.sendExamUpdateEmail(emailDto);
    }

    public void sendExamInvitationEmail(Account account, Exam exam) {
        buildAndSendExamInvitationEmailToQueue(account, exam);
    }

    public void sendExamInvitationCancelEmail(Account account, Exam exam) {
        buildAndSendExamInvitationCancelEmailToQueue(account, exam);
    }

    public void buildAndSendExamInvitationEmailToQueue(Account account, Exam exam) {
        EmailDto emailDto = new EmailDto();
        emailDto.setMailTo(account.getEmail());
        emailDto.setMailSubject("Exam Invitation!");
        emailDto.setContentType("text/plain; charset=\"utf-8\"");
        emailDto.setMailContent("You have been invited to " + exam.getTitle());

        rabbitMQPublisher.sendExamUpdateEmail(emailDto);
    }

    public void buildAndSendExamInvitationCancelEmailToQueue(Account account, Exam exam) {
        EmailDto emailDto = new EmailDto();
        emailDto.setMailTo(account.getEmail());
        emailDto.setMailSubject("Exam invitation canceled!");
        emailDto.setContentType("text/plain; charset=\"utf-8\"");
        emailDto.setMailContent("Your invitation has canceled for " + exam.getTitle());

        rabbitMQPublisher.sendExamUpdateEmail(emailDto);
    }
}