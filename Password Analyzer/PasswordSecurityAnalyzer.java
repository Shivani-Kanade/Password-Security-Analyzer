import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;
public class PasswordSecurityAnalyzer{
    static Set<String> commonPassword = new HashSet<>();
    static{
        commonPassword.add("password");
        commonPassword.add("12345678");
        commonPassword.add("qwerty123");
        commonPassword.add("admin123");
        commonPassword.add("password123");
        commonPassword.add("987654321");
        commonPassword.add("123456789");
        commonPassword.add("asdfghj");
    }
    public static void main(String[] args){
         Scanner sc = new Scanner(System.in);
        System.out.println("==========================================");
         System.out.println("     PASSWORD SECURITY ANALYZER ");
        System.out.println("==========================================");
        System.out.println("Enter your password");
        String password = sc.nextLine();
        boolean upper = false;
        boolean lower = false;
        boolean digit = false;
        boolean special = false;   
        for(char ch : password.toCharArray()){
            if(Character.isUpperCase(ch)) upper = true;
             else if(Character.isLowerCase(ch)) lower = true;
            else if(Character.isDigit(ch)) digit = true;
            else special = true;
        }
        boolean common = isCommonPassword(password);
        double entropy = calculateEntropy(password , upper , lower , digit , special , common);
        int score = calculateScore(password , upper , lower , digit , special , common);
        printReport(password , upper , lower , digit , special , common,score,entropy);
        sc.close();
    }
    static int calculateScore(String password ,  boolean upper ,  boolean lower ,  boolean digit ,  boolean special ,  boolean common){
        int score =0;
        if(password.length()>=8) score+=25;
        if(password.length()>=12) score+=15;
        if(password.length()>=16) score+=10;
        if(upper) score+=10;
        if(lower) score+=10;
        if(digit) score+=10;
        if(special) score+=20;
        if(common) score=0;
        return Math.min(100,score);
    }
    static boolean isCommonPassword(String password){
        return commonPassword.contains(password.toLowerCase());
    }
    static double calculateEntropy(String password ,  boolean upper ,  boolean lower ,  boolean digit ,  boolean special ,  boolean common){
     int poolSize = 0;
     if(upper) poolSize+=26;
     if(lower) poolSize+=26;
     if(digit) poolSize+=10;
     if(special) poolSize+=32;
        return  password.length()*(Math.log(poolSize)/Math.log(2));
        
    }
    static void printReport(String password ,  boolean upper ,  boolean lower ,  boolean digit ,  boolean special ,  boolean common , int score , double entropy){
    String strength ;
        if(common || score < 30)
           strength = "weak";
        else if (score<60)
           strength = "Moderate";
        else if (score<80)
           strength = "Strong";
        else 
            strength = "Very Strong";
    System.out.println("==========================================");
    System.out.println("             Security Report              ");
    System.out.println("Score: " + score + "/100");
    System.out.println("Strength: " + strength);
    System.out.println("Length: " + password.length());
    System.out.println("Upper: " + yesNo(upper));
    System.out.println("Lower: " + yesNo(lower));
    System.out.println("Digit: " + yesNo(digit));
    System.out.println("Special: " + yesNo(special));
    System.out.printf("Entropy: %.2f bits%n " , entropy);
    System.out.println("==========================================");
    if(common) System.out.println("Tip: Avoid common passwords.");
    else if(password.length()<8) System.out.println("Tip: Use 8+ characters.");
    else if(upper||lower|| digit|| special) System.out.println("Tip: Improve character variety.");
    else System.out.println("Tip: Avoid reusing passwords.");    
    }
    static String yesNo(boolean value){
        return value? "Yes" :"No";
    }
}