import React from "react";
import axios from "axios"
import { useState } from "react";
import { Button, Form, FormControl, Container, Alert, Table, Col, Row } from "react-bootstrap";

function Logi({setStatus, username, password}) { 
     const [textArea, setTextArea] = useState("");
    
    // do tabeli
    const [zamianyData, setZamianyData] = useState([]);

    async function getLogi() {
        const credentials = {username: username, password: password};
        try {
            const response = await axios.get
            (
                "http://localhost:8080/api/admin/logi/wszystkie",
                {
                    auth: credentials,
                    headers: {
                        "Content-Type": "application/json",
                    },
                }
            );

            const data = Array.isArray(response.data) ? response.data : [];
            setTextArea(dataNaTekst(response.data).join("\n"));
            setZamianyData(response.data);
            setStatus("Pobrano logi.");
            console.info(textArea);
        } catch (error) {
            const errorMessage = error.response?.data?.message || error.message || "Wystąpił nieznany błąd";
            setStatus(`Błąd: ${errorMessage}`);
            console.error("Błąd podczas pobierania logów:", error);
        }
    }

    function dataNaTekst(data) {
        return data.map(
            (mojlog, i) => 
            {
                return (`${i + 1}. ${mojlog.message} | ${mojlog.timestamp} | ${mojlog.level}`);
            }
        );
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
                    <Button variant="primary" onClick={(() => getLogi())}>
                        Wczytaj logi
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
                        <th>Treść</th>
                        <th>Data</th>
                        <th>Level</th>
                    </tr>
                </thead>
                <tbody>
                    {zamianyData.length > 0 ? (
                        zamianyData.map((mojlog, i) => (
                        <tr key={i}>
                            <td>{mojlog.id}</td>
                            <td>{mojlog.message}</td>
                            <td>{mojlog.timestamp}</td>
                            <td>{mojlog.level}</td>
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

export default Logi;
