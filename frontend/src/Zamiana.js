import React from "react";
import axios from "axios"
import { useState } from "react";
import { Button, Form, FormControl, Container, Alert } from "react-bootstrap";

function Zamiana({setStatus, username, password}) { 
    const [wartoscWejsciowa, setWartoscWejsciowa] = useState(1);
    const [wartoscWynikowa, setWartoscWynikowa] = useState(180/3.1415);
    const [jednostkaWejsciowa, setJednostkaWejsciowa] = useState("RADIANY");
    const [jednostkaWynikowa, setJednostkaWynikowa] = useState("STOPNIE");
    const jednostki = ["RADIANY", "STOPNIE"];

    async function zamien(event) {
        event.preventDefault();
        const credentials = {username: username, password: password};
        if (wartoscWejsciowa && jednostkaWejsciowa && jednostkaWynikowa) {
            try {
                const response = await axios.post(
                    "http://localhost:8080/api/client/zamiany/konwertuj",
                    {
                        wartoscWejsciowa,
                        jednostkaWejsciowa,
                        jednostkaWynikowa
                    },
                    {
                        auth: credentials,
                        headers: {
                            "Content-Type": "application/json",
                        },
                    }
                );
                setWartoscWynikowa(response.data.toFixed(3));
            } catch (error) {

            }
        } else {
            setStatus("Wszystkie pola muszą być wypełnione!");
        }
    }
    
    return (
          <Container>
                    <Alert variant="info">
                        <Alert.Heading className="text-center">
                            <strong>Zamiana radianów</strong>
                        </Alert.Heading>
                    </Alert>

                    <Form.Label>Zamiana:</Form.Label>
                    <Form onSubmit={zamien}>
                        <Form.Group className="mb-3 d-flex align-items-center">
                            <Form.Control
                                type="number"
                                value={wartoscWejsciowa}
                                onChange={(e) => setWartoscWejsciowa(e.target.value)}
                                placeholder="Wpisz wartość"
                                style={{ width: "150px", marginRight: "10px" }}
                            />
                            <Form.Select
                                value={jednostkaWejsciowa}
                                onChange={(e) => setJednostkaWejsciowa(e.target.value)}
                                style={{ width: "150px", marginRight: "10px" }}
                            >
                                <option value="">Wybierz</option>
                                {jednostki.map((jednostki) => (
                                    <option key={jednostki} value={jednostki}>
                                        {jednostki}
                                    </option>
                                ))}
                            </Form.Select>
                            <span style={{ margin: "0 10px" }}> = </span>
                            <Form.Control
                                type="text"
                                value={wartoscWynikowa}
                                readOnly
                                placeholder="Wynik"
                                style={{ width: "150px", marginRight: "10px" }}
                            />
                            <Form.Select
                                value={jednostkaWynikowa}
                                onChange={(e) => setJednostkaWynikowa(e.target.value)}
                                style={{ width: "150px" }}
                            >
                                <option value="">Wybierz</option>
                                {jednostki.map((jednostka) => (
                                    <option key={jednostka} value={jednostka}>
                                        {jednostka}
                                    </option>
                                ))}
                            </Form.Select>
                        </Form.Group>
                        <Button variant="primary" type="submit">Wykonaj wymianę</Button>
                    </Form>
                </Container>
        );
}

export default Zamiana;
    