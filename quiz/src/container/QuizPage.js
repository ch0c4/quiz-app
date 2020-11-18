import React, { useContext, useState } from 'react';
import { GlobalContext } from '../context/GlobalState';
import { Grid, Container, Segment, Checkbox, Button } from 'semantic-ui-react';
import { answeredQuestion, fetchStat } from '../services/QuizApi';
import { answerQuestionAction, finishQuizAction } from '../context/Action';

function QuizPage(props) {
  const { currentQuiz, customer, dispatch } = useContext(GlobalContext);
  const [nextQuestion, setNextQuestion] = useState(false);
  const [answer, setAnswer] = useState('');

  function handleSelect(answerId) {
    setAnswer(currentQuiz.answers[answerId]);
    setNextQuestion(true);
  }

  async function handleAnwerQuestion() {
    const answerTime = Date.now() - currentQuiz.time;
    const nextQuestion = await answeredQuestion(
      customer.id,
      currentQuiz.quizId,
      currentQuiz.questionId,
      answer,
      answerTime
    );
    if (nextQuestion.completed) {
      const data = await fetchStat(currentQuiz.quizId);
      dispatch(finishQuizAction(data));
      props.history.push('/finishQuiz');
      return;
    }

    const nextCurrentQuiz = {
      quizId: currentQuiz.quizId,
      questionId: nextQuestion.id,
      question: nextQuestion.question,
      answers: shuffle(nextQuestion.answers),
      time: currentQuiz.time,
    };
    dispatch(answerQuestionAction(nextCurrentQuiz));
    setAnswer('');
    setNextQuestion(false);
  }

  function unescapeHtml(safe) {
    return safe
      .replace(/&amp;/g, '&')
      .replace(/&lt;/g, '<')
      .replace(/&gt;/g, '>')
      .replace(/&quot;/g, '"')
      .replace(/&#039;/g, "'");
  }

  function shuffle(a) {
    for (let i = a.length - 1; i > 0; i--) {
      const j = Math.floor(Math.random() * (i + 1));
      [a[i], a[j]] = [a[j], a[i]];
    }
    return a;
  }

  return (
    <Container>
      <br />
      <Grid>
        <Grid.Row columns={1}>
          <Grid.Column>
            <Segment textAlign="center" size="massive">
              {unescapeHtml(currentQuiz.question)}
            </Segment>
          </Grid.Column>
        </Grid.Row>
        <Grid.Row columns={2}>
          <Grid.Column>
            <Segment textAlign="center" onClick={() => handleSelect(0)} style={{ cursor: 'pointer' }}>
              <Checkbox
                label={unescapeHtml(currentQuiz.answers[0])}
                onChange={() => handleSelect(0)}
                checked={currentQuiz.answers[0] === answer}
              />
            </Segment>
          </Grid.Column>
          <Grid.Column>
            <Segment textAlign="center" onClick={() => handleSelect(1)} style={{ cursor: 'pointer' }}>
              <Checkbox
                label={unescapeHtml(currentQuiz.answers[1])}
                onChange={() => handleSelect(1)}
                checked={currentQuiz.answers[1] === answer}
              />
            </Segment>
          </Grid.Column>
        </Grid.Row>
        <Grid.Row columns={2}>
          <Grid.Column>
            <Segment textAlign="center" onClick={() => handleSelect(2)} style={{ cursor: 'pointer' }}>
              <Checkbox
                label={unescapeHtml(currentQuiz.answers[2])}
                onChange={() => handleSelect(2)}
                checked={currentQuiz.answers[2] === answer}
              />
            </Segment>
          </Grid.Column>
          <Grid.Column>
            <Segment textAlign="center" onClick={() => handleSelect(3)} style={{ cursor: 'pointer' }}>
              <Checkbox
                label={unescapeHtml(currentQuiz.answers[3])}
                onChange={() => handleSelect(3)}
                checked={currentQuiz.answers[3] === answer}
              />
            </Segment>
          </Grid.Column>
        </Grid.Row>
      </Grid>
      {nextQuestion && (
        <Grid centered columns={4}>
          <Grid.Column>
            <Button size="massive" primary onClick={handleAnwerQuestion}>
              Next question ?
            </Button>
          </Grid.Column>
        </Grid>
      )}
    </Container>
  );
}

export default QuizPage;
