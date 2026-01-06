package Excepciones;

public class FaltaPuntoException extends RuntimeException {
	
	public FaltaPuntoException() {
	}
	
    @Override
    public String getMessage() {
        return "Falta colocar el punto (.) en el correo electrónico.";
    }
}