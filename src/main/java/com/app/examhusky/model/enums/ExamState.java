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
 *     <li>{@code ENDED}: All participants have submitted their answers and examiner or admin ended the exam.</li>
 *     <li>{@code PUBLISH_RESULT}: The result of the exam will be published via email and also be available at
 *     candidate dashboard, this action will be performed by admin or examiner.
 *     .</li>
 * </ul>
 * </p>
 *
 */
public enum ExamState {
    PENDING, PUBLISHED, ON_GOING, ENDED, PUBLISH_RESULT;
}
