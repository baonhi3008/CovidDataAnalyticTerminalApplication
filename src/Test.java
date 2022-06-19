import java.util.Scanner;
import java.util.logging.Logger;

public class Test {
//    private static final Logger logger = Logger.getLogger(Test.class);

    public static void main(String[] args) throws Exception {

        try {

            System.out.println("Final number: " + canDivide(12));
        } catch (Exception e) {
            // Display custom message to the user
            System.out.println(e.getMessage());

        }

    }

    public static boolean checkRemainder(int size, int n){
        int remainder = size%n;
        if (remainder == 0){
            return true;
        }
        else{
            return false;
        }
    }
    public static int canDivide(int n) {
        Scanner scanner= new Scanner(System.in);
        boolean flag = false;
        int number =0;
        int finalNum = 0;

        String error = "Cannot divide";
        while(!flag) {
            try {

                System.out.println("Enter a number to be divided: ");
                number =scanner.nextInt();
                scanner.nextLine();

            } catch(Exception e) {
                System.out.println("Invalid input try again");
                scanner.nextLine();
                // cause to consume already caught Exception
            }
            if(checkRemainder(n,number)){
                flag = true;
            }
            else{
                System.out.println("Cannot group. Try again");
                flag = false;
            }
            finalNum = number;
        }
        return finalNum;
    }
}
class DivideException extends Exception {
    private String code;

    public DivideException(String code, String message) {
        super(message);
        this.setCode(code);
    }
    public DivideException(String code, String message, Throwable cause) {
        super(message, cause);
        this.setCode(code);
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
