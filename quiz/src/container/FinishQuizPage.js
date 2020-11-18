import React, { useContext, Fragment } from 'react';
import { GlobalContext } from '../context/GlobalState';
import { Segment, Statistic, Card, Container, Icon } from 'semantic-ui-react';
import { Link } from 'react-router-dom';

function FinishQuizPage() {
  const { finishQuiz } = useContext(GlobalContext);

  function unescapeHtml(safe) {
    return safe
      .replace(/&amp;/g, '&')
      .replace(/&lt;/g, '<')
      .replace(/&gt;/g, '>')
      .replace(/&quot;/g, '"')
      .replace(/&#039;/g, "'");
  }

  console.log(finishQuiz);

  return (
    <Container>
      <br />
      {finishQuiz.totalCorrectAnswer < 10 && (
        <Fragment>
          <Segment inverted color="red" textAlign="center" size="massive">
            Wrong, you have bad answers
          </Segment>
          <Segment textAlign="center">
            <Statistic>
              <Statistic.Value>{finishQuiz.totalCorrectAnswer} / 10</Statistic.Value>
              <Statistic.Label>Correct answer</Statistic.Label>
            </Statistic>
          </Segment>
          <Card.Group centered>
            {finishQuiz.questions.map((question) => (
              <Card>
                <Card.Content header={unescapeHtml(question.question)} />
                <Card.Content description={`Correct Answer : ${unescapeHtml(question.correctAnswers)}`} />
                <Card.Content extra>
                  {question.isCorrect && <Icon name="thumbs up outline" />}
                  {!question.isCorrect && <Icon color="red" name="thumbs down outline" />}
                </Card.Content>
              </Card>
            ))}
          </Card.Group>
        </Fragment>
      )}
      {finishQuiz.totalCorrectAnswer === 10 && (
        <Segment inverted color="green">
          Congratulation, you finish the quiz in {finishQuiz.timeFinish / 1000} seconds
        </Segment>
      )}
      <Link to="/dashboard">Back to dashboard</Link>
    </Container>
  );
}

export default FinishQuizPage;
