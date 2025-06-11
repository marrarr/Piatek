import './App.css';
import { useState } from 'react';
import Container from 'react-bootstrap/Container';
import Alert from 'react-bootstrap/Alert';
import Button from 'react-bootstrap/Button';
import Tab from 'react-bootstrap/Tab';
import Tabs from 'react-bootstrap/Tabs';
import Card from 'react-bootstrap/Card';

import Logowanie from './Logowanie';
import Zamiana from './Zamiana';
import Historia from './Historia';
import Statystyki from './Statystyki';
import Logi from './Logi';

function App() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [zalogowany, setZalogowany] = useState(false);
  const [activeTab, setActiveTab] = useState("zamiana");
  const [status, setStatus] = useState("");

  if (!zalogowany) {
    return (
      <div className="centered-login">
        <Card className="p-4 shadow-sm login-card">
          <h2 className="mb-4 text-center">Logowanie</h2>
          <Logowanie
            setStatus={setStatus}
            setUsername={setUsername}
            setPassword={setPassword}
            setZalogowany={setZalogowany}
          />
        </Card>
      </div>
    );
  } else {
    return (
      <Container className="pt-4">
        <Card className="p-3 mb-3 shadow-sm">
          <Alert variant="light" className="mb-2">
            <div className="position-relative d-flex align-items-center">
              <div className="w-100 text-center position-absolute">
                <strong className="mb-0">Witaj, {username}!</strong>
              </div>
              <div className="ms-auto">
                <Button
                  variant="outline-danger"
                  onClick={() => {
                    setUsername("");
                    setPassword("");
                    setZalogowany(false);
                  }}>Wyloguj</Button>
              </div>
            </div>
          </Alert>
          <Alert variant="info" className="text-center">
            <strong>Status: {status}</strong>
          </Alert>
        </Card>

        <Tabs
          activeKey={activeTab}
          onSelect={(k) => setActiveTab(k)}
          className="mb-4 custom-tabs"
          justify
        >
          <Tab eventKey="zamiana" title="Zamiana">
            <Zamiana setStatus={setStatus} username={username} password={password} />
          </Tab>
          <Tab eventKey="historia" title="Historia">
            <Historia setStatus={setStatus} username={username} password={password} />
          </Tab>
          <Tab eventKey="statystyki" title="Statystyki">
            <Statystyki setStatus={setStatus} username={username} password={password} />
          </Tab>
          <Tab eventKey="logi" title="Logi">
            <Logi setStatus={setStatus} username={username} password={password} />
          </Tab>
        </Tabs>

        
      </Container>
    );
  }
}

export default App;
