package com.app.examhusky.model.enums;

import java.util.EnumSet;


/**
 * Enumeration representing the possible states of an exam.
 * <p>
 * The states include:
 * <ul>
 *     <li>{@code PENDING}: The exam is pending and has not started yet.</li>
 *     <li>{@code PUBLISHED}: The exam is published and available for participants.</li>
 *     <li>{@code ON_GOING}: The exam is currently in progress.</li>
 *     <li>{@code ALL_ANSWER_SUBMITTED}: All participants have submitted their answers.</li>
 *     <li>{@code EXAMINATION_COMPLETE}: The examination process is complete.</li>
 *     <li>{@code PUBLISH_RESULT}: The result of the exam will be published via email and also be available at
 *     candidate dashboard
 *     .</li>
 * </ul>
 * </p>
 * <p>
 * Additionally, this enum defines two sets of states:
 * <ul>
 *     <li>{@code EDITABLE_STATES}: States during which the exam can be edited (PENDING,
 *     PUBLISHED).</li>
 *     <li>{@code AUTOMATED_STATES}: Automated states that represent different phases of the examination
 *     process (ON_GOING, ALL_ANSWER_SUBMITTED, EXAMINATION_COMPLETE).</li>
 * </ul>
 * </p>
 *
 */
public enum ExamState {
    PENDING, PUBLISHED, ON_GOING, ALL_ANSWER_SUBMITTED, EXAMINATION_COMPLETE, PUBLISH_RESULT;

    private static final EnumSet<ExamState> EDITABLE_STATES = EnumSet.of(PENDING, PUBLISHED, PUBLISH_RESULT);

    private static final EnumSet<ExamState> AUTOMATED_STATES = EnumSet.of(ON_GOING,
            ALL_ANSWER_SUBMITTED, EXAMINATION_COMPLETE);

    public static EnumSet<ExamState> getEditableStates() {
        return EnumSet.copyOf(EDITABLE_STATES);
    }

    public static EnumSet<ExamState> getAutomatedStates() {
        return EnumSet.copyOf(AUTOMATED_STATES);
    }
}
