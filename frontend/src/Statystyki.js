import React from "react";
import axios from "axios"
import { useState } from "react";
import { Button, Form, FormControl, Container, Alert, Col, Row, Table } from "react-bootstrap";


function Statystyki({setStatus, username, password}) { 
    const [textArea, setTextArea] = useState("");

    // do tabeli
    const [zamianyData, setZamianyData] = useState([]);
    const [statystykaWidok, setStatystykaWidok] = use("");

    // do wyszukiwania historii dla konkretnego użytkownika (dla admina)
    const [usernameFilter, setUsernameFilter] = useState("");

    async function getStatystykiIloscPerUser() {
        const credentials = {username: username, password: password};
            try {
                const response = await axios.get(
                    "http://localhost:8080/api/admin/zamiany/statIloscPerUser",
                    {
                        auth: credentials,
                        headers: {
                            "Content-Type": "application/json",
                        },
                    }
                );

                const data = Array.isArray(response.data) ? response.data : [];

                const tekstStatystyki = data.map(
                    (stat, i) => `${i + 1}. ${stat.username} wykonał ${stat.ilosc} zamian.`
                ).join("\n"); 

                setTextArea(tekstStatystyki);
                setZamianyData(response.data);
                setStatystykaWidok("stat1");
                setStatus("Pobrano statystyki.");
                console.info(textArea);
            } catch (error) {
                setStatus(error);
                console.error("Błąd podczas pobierania statystyk:", error);
            }
    }

    async function getStatystykiIloscPerUserAndJednostka() {
        const credentials = {username: username, password: password};
            try {
                const response = await axios.get(
                    "http://localhost:8080/api/admin/zamiany/statIloscPerUserAndJednostka",
                    {
                        auth: credentials,
                        headers: {
                            "Content-Type": "application/json",
                        },
                    }
                );

                const data = Array.isArray(response.data) ? response.data : [];

                const tekstStatystyki = data.map(
                    (stat, i) => `${i + 1}. ${stat.username} wykonał ${stat.ilosc} zamian.`
                ).join("\n"); 

                setTextArea(tekstStatystyki);
                setZamianyData(response.data);
                setStatystykaWidok("stat2");
                setStatus("Pobrano statystyki.");
                console.info(textArea);
            } catch (error) {
                setStatus(error);
                console.error("Błąd podczas pobierania statystyk:", error);
            }
    }

    
    return (
          <Container>
                    <Alert variant="info">
                        <Alert.Heading>
                            <strong>Historia</strong>
                        </Alert.Heading>
                    </Alert>

                    {/*przyciski  */}
            <Row className="mb-3">
                <Col>
                    <Button variant="primary" onClick={(() => getStatystykiIloscPerUser())}>
                        Ilość wymian każdego użytkownika
                    </Button>
                </Col>
                <Col>
                    <Button variant="primary" onClick={(() => getStatystykiIloscPerUserAndJednostka())}>
                        Ilość wszystkich typów wymian
                    </Button>
                </Col>
            </Row>

                    
                

            <Form.Group className="mb-4">
                <Form.Label>Wyniki (tekstowo):</Form.Label>
                <Form.Control as="textarea" rows={6} value={textArea} readOnly />
            </Form.Group>

            
            <Table striped bordered hover>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Użytkownik</th>
                        <th>Ilość</th>
                    </tr>
                </thead>
                <tbody>
                    {zamianyData.length > 0 ? (
                        zamianyData.map((zamiana, i) => (
                        <tr key={i}>
                            <td>{i + 1}</td>
                            <td>{zamiana.username}</td>
                            <td>{zamiana.ilosc}</td>
                        </tr>
                    ))
                ) : (
                    <tr>
                        <td colSpan="8" className="text-center">Brak danych do wyświetlenia</td>
                    </tr>
                )}
            </tbody>
        </Table>
    </Container>
    );
}

export default Statystyki;
    