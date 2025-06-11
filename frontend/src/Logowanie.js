import React from "react";
import axios from "axios"
import { useState } from "react";
import { Button, Form } from "react-bootstrap";

function Logowanie({ setStatus, setUsername, setPassword, setZalogowany }) { 
    // dane logowania z formularza, do wyslanie
    const [formUsername, setFormUsername] = useState("");
    const [formPassword, setFormPassword] = useState("");

    
    const zaloguj = () => {
        try {
            let username = formUsername;
            let password = formPassword;

            axios.get("http://localhost:8080/api/auth/login", {
                auth: {
                    username: formUsername,
                    password: formPassword
                }
            }).then(() => {
                setZalogowany(true);
                setUsername(formUsername);
                setPassword(formPassword);
            }).catch(() => {
                alert("Niepoprawny login lub hasło");
            });

        //     const response = await axios.post("http://localhost:8081/api/auth/login", {
        //         username, // mozliwe bedzie blad nazw!!!
        //         password
        //     });

        //     if (response.status === 200) {
        //         setUserName(username);
        //         setPassword(password);
        //         setStatus(response.data);
        //         setZalogowany(true);
        //     } else {
        //         alert("Niepoprawny login lub hasło");
        //     }
        } catch (error) {
            console.error("Błąd logowania: ", error);
            alert("Błąd logowania. Spróbuj później.");
        }
    };

    return (
        <div className="container mt-3">
            <h2>Logowanie</h2>
            <Form>
                <Form.Group controlId="formUserName" className="mb-3">
                    <Form.Label>Login</Form.Label>
                    <Form.Control
                        type="text"
                        placeholder="Login"
                        value={formUsername}
                        onChange={(e) => setFormUsername(e.target.value)}
                    />
                </Form.Group>
        
                <Form.Group controlId="formPassword" className="mb-3">
                    <Form.Label>Hasło</Form.Label>
                    <Form.Control
                        type="password"
                        placeholder="Hasło"
                        value={formPassword}
                        onChange={(e) => setFormPassword(e.target.value)}
                    />
                </Form.Group>
ś
                <Form.Group>
                    <Button variant="primary" onClick={zaloguj}>Zaloguj się</Button>
                </Form.Group>
            </Form>
        </div>
    );
} 

export default Logowanie;