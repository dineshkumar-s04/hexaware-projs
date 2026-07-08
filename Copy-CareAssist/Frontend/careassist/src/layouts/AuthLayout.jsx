import { Container, Row, Col, Card } from "react-bootstrap";

function AuthLayout({ children }) {
    return (
        <Container
            fluid
            className="min-vh-100 d-flex align-items-center justify-content-center bg-light"
        >
            <Row className="w-100 justify-content-center">
                <Col xs={11} sm={9} md={7} lg={5} xl={4}>
                    <Card className="shadow border-0 rounded-4">
                        <Card.Body className="p-5">
                            {children}
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
}

export default AuthLayout;