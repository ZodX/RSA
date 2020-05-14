import java.math.BigInteger;
import java.util.Scanner;

public class rsa extends algorythms {

    private static BigInteger p,q,n,fn, minLimit = new BigInteger("20"), maxLimit = new BigInteger("5000000"), e, d, m, c, user_input;
    private static Scanner sc = new Scanner(System.in);

    private static void generateKey() {
        boolean gotTwoPrimes = false;
        BigInteger[] back = new BigInteger[3];
        System.out.println("Generating p and q...");
        p = generateBigInteger(minLimit, maxLimit);
        q = generateBigInteger(minLimit, maxLimit);
        while (gotTwoPrimes == false) {
            while (true) {
                p = generateBigInteger(minLimit, maxLimit);
                q = generateBigInteger(minLimit, maxLimit);
                if (p.mod(BigInteger.TWO).equals(BigInteger.ONE) && q.mod(BigInteger.TWO).equals(BigInteger.ONE))
                    break;
            }
            for (int i = 0; i < 3; i++) {
                if (mR(p, new BigInteger("2")) == false || mR(p, new BigInteger("6")) == false || mR(p, new BigInteger("13")) == false ||
                    mR(q, new BigInteger("2")) == false || mR(q, new BigInteger("6")) == false || mR(q, new BigInteger("13")) == false)
                    break;
                else   
                    gotTwoPrimes = true; 
            }
        }
        n = p.multiply(q);
        fn = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
    
        System.out.println("Generating e...");
        while (true) {
            // Generate an even number.
            while (true) {
                e = generateBigInteger(new BigInteger("3"), new BigInteger("50"));
                if (e.mod(BigInteger.TWO).equals(BigInteger.ONE))
                    break;
            }
            // Check if they are coprimes. (with EEA)
            back = eEA(e, fn);
            if (back[0].equals(BigInteger.ONE))
                break;
        }

        // Generate d.
        if (back[1].compareTo(BigInteger.TWO) == -1) {
            d = back[1].add(fn);
        } else {
            d = back[1];
        }
    }
    
    private static BigInteger encrypt(BigInteger m) {
        return fME(m, e, n);
    }

    private static BigInteger decrypt(BigInteger c) {
        return fME(c, d, n);
    }

    public static void main(String[] args) {
        // Key generation
        generateKey();
        System.out.println("p: " + p + " q: " + q + "\nn: " + n + "\nfn: " + fn + "\ne: " + e + "\nd: " + d);

        // Ask the user for an input.
        System.out.println("Please enter an input:");
        while (true) {
            user_input = sc.nextBigInteger();
            if (user_input.compareTo(n) >= 0)
                System.out.println("n is: " + n + "\nPlease enter a smaller input than n.");
            else
                break;
        }

        // Ask for encrypt or decrypt (e/d)
        System.out.println("Please enter what you want to do with it: (for encryption type: \"e\", for decryption type: \"d\"");
        switch (sc.next()) {
            case "e":
                c = encrypt(user_input);  
                System.out.println("The encrypted result is: " + c);  
                m = decrypt(c);
                System.out.println("Just to make sure my decrypt method works fine too, here is your message decrypted back: " + m);
                break;
            
            case "d":
                m = decrypt(user_input);
                System.out.println("The decrypted result is: " + m);  
                c = encrypt(m);
                System.out.println("Just to make sure my encrypt method works fine too, here is your message encrypted back: " + c);
                break;

            default:
                System.out.println("Invalid input.");
                break;
        }

        sc.close();
    }
}