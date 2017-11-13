package com.teamtreehouse.techdegree.overboard.model;

import com.teamtreehouse.techdegree.overboard.exc.AnswerAcceptanceException;
import com.teamtreehouse.techdegree.overboard.exc.VotingException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;


public class UserTest {
    private Board board;
    private User answerer;
    private User questioner;
    private Question question;
    private Answer answer;

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Before
    public void setUp() throws Exception {
        board = new Board("Understanding our galaxy");
        questioner = new User(board, "Sam");
        answerer = new User(board, "Josh");
        question = questioner.askQuestion("How many light years across is the Milky Way?");
        answer = answerer.answerQuestion(question, "100,000 light years across");
    }

    @Test
    public void questionersReputationGoesUpByFiveWhenAnswerIsUpvoted () throws Exception {
        answerer.upVote(question);

        assertEquals(questioner.getReputation(), 5);
    }

    @Test
    public void answerersReputationGoesUpByTenWhenAnswerIsUpvoted() {
        questioner.upVote(answer);

        assertEquals(answerer.getReputation(), 10);
    }

    @Test
    public void answerersReputationGoesUpBy15WhenIsExcepted() {
        questioner.acceptAnswer(answer);

        assertEquals(answerer.getReputation(), 15);
    }

    @Test (expected = VotingException.class)
    public void userCanNotUpVoteTheirOwnQuestionOrAnswer() throws Exception {
        questioner.upVote(question);
    }

    @Test (expected = AnswerAcceptanceException.class)
    public void onlyOriginalQuestionerCanAcceptAnswer() throws Exception {
        answerer.acceptAnswer(answer);
        thrown.expectMessage("Only original questioner can accept this answer as it is their question");
    }
}
