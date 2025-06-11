import React from "react";
import axios from "axios"
import { useState } from "react";
import { Button, Form, FormControl, Container, Alert, Col, Row, Table } from "react-bootstrap";


function Historia({setStatus, username, password}) { 
    const [textArea, setTextArea] = useState("");

    // do tabeli
    const [zamianyData, setZamianyData] = useState([]);

    // do wyszukiwania historii dla konkretnego użytkownika (dla admina)
    const [usernameFilter, setUsernameFilter] = useState("");

    async function getHistoria(url) {
        const credentials = {username: username, password: password};
            try {
                const response = await axios.get(
                    url,
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
                setStatus("Pobrano historię wymian użytkownika.");
                console.info(textArea);
            } catch (error) {
                setStatus(error);
                console.error("Błąd podczas pobierania historii zamian:", error);
            }
        }

    async function usunRekord(id) {
        const credentials = {username: username, password: password};
       
        try {
            const response = await axios.delete(
                `http://localhost:8080/api/admin/zamiany/usuwanie/${id}`,
                {
                    auth: credentials,
                    headers: {
                        "Content-Type": "application/json",
                    },
                }
            );

            if (response.status === 204) {
                // zrobienie nowej listy bez rekordu z tym id
                const nowaLista = zamianyData.filter(zamiana => zamiana.id !== id);
                setZamianyData(nowaLista);
                setTextArea(dataNaTekst(nowaLista).join("\n"));
                console.info("Poprawnie usunięto rekord.");
            } else {
                console.info("Nieudało się usunąć rekordu.");
            }
        } catch (error) {
            setStatus(error);
            console.error("Błąd podczas usuwania:", error);
        }   
    }

    function dataNaTekst(data) {
        return data.map(
            (zamiana, i) => 
            {
                return (`${i + 1}. [${zamiana.czasZamiany}] ${zamiana.username} zamienił: ` + 
                `${zamiana.wartoscWejsciowa} ${zamiana.jednostkaWejsciowa} na ` + 
                `${zamiana.wartoscWynikowa} ${zamiana.jednostkaWynikowa}`
                );
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
                    <Button variant="primary" onClick={(() => getHistoria("http://localhost:8080/api/client/zamiany/moje"))}>
                        Moje wymiany
                    </Button>
                </Col>
                <Col>
                    <Button variant="success" onClick={(() => getHistoria("http://localhost:8080/api/admin/zamiany/wszystkie"))}>
                        Wszystkie wymiany
                    </Button>
                </Col>
                <Col xs="auto">
                    <Form inline="true" className="d-flex">
                        <Form.Control
                            type="text"
                            placeholder="Nazwa użytkownika"
                            value={usernameFilter}
                            onChange={(e) => setUsernameFilter(e.target.value)}
                            style={{ marginRight: "10px" }}
                        />
                        <Button variant="warning" onClick={() =>
                            getHistoria(`http://localhost:8080/api/admin/zamiany/uzytkownik/${usernameFilter}`)
                        }>
                            Wymiany użytkownika
                        </Button>
                    </Form>
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
                        <th>Data</th>
                        <th>Użytkownik</th>
                        <th>Wartość</th>
                        <th>Jedn. Wartości</th>
                        <th>Wynik</th>
                        <th>Jedn. Wyniku</th>
                        <th>Akcja</th>
                    </tr>
                </thead>
                <tbody>
                    {zamianyData.length > 0 ? (
                        zamianyData.map((zamiana, i) => (
                        <tr key={i}>
                            <td>{zamiana.id}</td>
                            <td>{zamiana.czasZamiany}</td>
                            <td>{zamiana.username}</td>
                            <td>{zamiana.wartoscWejsciowa}</td>
                            <td>{zamiana.jednostkaWejsciowa}</td>
                            <td>{zamiana.wartoscWynikowa}</td>
                            <td>{zamiana.jednostkaWynikowa}</td>
                            <td>
                                <Button variant="danger" onClick={() => usunRekord(zamiana.id)}>
                                    Usuń
                                </Button>
                            </td>
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

export default Historia;
    