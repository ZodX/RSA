import java.math.BigInteger;
import java.util.Random;

public class algorythms {

    protected static BigInteger generateBigInteger(BigInteger minLimit, BigInteger maxLimit) {
        BigInteger bigInteger = maxLimit.subtract(minLimit);

        Random randNum = new Random();
        int len = maxLimit.bitLength();
        BigInteger num = new BigInteger(len, randNum);

        if (num.compareTo(minLimit) < 0) 
            num = num.add(minLimit);
        if (num.compareTo(bigInteger) >= 0)
            num = num.mod(bigInteger).add(minLimit);
        
        return num;
    }

    protected static BigInteger[] eEA(BigInteger num1, BigInteger num2) {
        BigInteger help, qk, xk, yk, xk_prev, yk_prev, xk_next, yk_next, lnko, num0, k = BigInteger.ZERO, lnko_index, x, y;

        qk = num1.divide(num2);
        xk_prev = BigInteger.ONE;
        yk_prev = BigInteger.ZERO;
        xk = BigInteger.ZERO;
        yk = BigInteger.ONE;
        num0 = num1;
        while (!num2.equals(BigInteger.ZERO)) {
            
            if (k.compareTo(BigInteger.ZERO) == 1) {
                xk_next = xk.multiply(qk).add(xk_prev);
                xk_prev = xk;
                xk = xk_next;
                
                yk_next = yk.multiply(qk).add(yk_prev);
                yk_prev = yk;
                yk = yk_next;

                num0 = num1;
            } 
            
            help = num1.mod(num2);
            num1 = num2;
            num2 = help; 
            qk = num0.divide(num1);
            k = k.add(BigInteger.ONE);
        }
        lnko_index = k;
        lnko = num1;

        x = new BigInteger("-1").pow(lnko_index.intValue()).multiply(xk);
        y = new BigInteger("-1").pow(lnko_index.add(BigInteger.ONE).intValue()).multiply(yk);

        BigInteger[] back = new BigInteger[3];
        back[0] = lnko;
        back[1] = x;
        back[2] = y;
        return back;
    } 

    protected static BigInteger[] fMEBinary(BigInteger a, BigInteger b, BigInteger m, BigInteger nth_pow) {
        BigInteger[] values = new BigInteger[nth_pow.intValue() + 1];

        values[0] = a.pow(BigInteger.ONE.intValue()).mod(m);
        for (BigInteger i = BigInteger.ONE; i.compareTo(nth_pow.add(BigInteger.ONE)) == -1; i = i.add(BigInteger.ONE))
            values[i.intValue()] = values[i.subtract(BigInteger.ONE).intValue()].pow(2).mod(m);

        return values;
    }
    
    protected static BigInteger fME(BigInteger a, BigInteger b, BigInteger m) {
        BigInteger help = b, nth_pow = BigInteger.ZERO, multies = BigInteger.ONE;
        BigInteger[] result;
        boolean isPow = false;
        String b_binary;
        while (true) {
            if (help.equals(BigInteger.ONE)) {
                isPow = true;
                break;
            }
            if (help.mod(BigInteger.TWO) != BigInteger.ZERO) {
                break;
            }
            help = help.divide(BigInteger.TWO);
            nth_pow = nth_pow.add(BigInteger.ONE);
        }
 
        if (isPow == true) {
            result = fMEBinary(a, b, m, nth_pow);

            return result[result.length - 1];
        }
        
        //Step 1
        b_binary = b.toString(2);
        
        //Step 2
        result = fMEBinary(a, b, m, BigInteger.valueOf(b_binary.length() - 1));

        //Step 3
        for (BigInteger i = BigInteger.valueOf(b_binary.length() - 1); i.compareTo(BigInteger.ZERO) >= 0; i = i.subtract(BigInteger.ONE))
            if (b_binary.charAt(i.intValue()) == '1')
                multies = multies.multiply(result[i.subtract(BigInteger.valueOf(b_binary.length() - 1)).abs().intValue()]);

        return multies.mod(m);
    }

    protected static boolean mR(BigInteger n, BigInteger a) {
        BigInteger s = BigInteger.ZERO,d,num;

        if (!(a.compareTo(n) == -1))
            throw new ArithmeticException("Invalid bases are given");
        if (BigInteger.ZERO.equals(n.mod(BigInteger.TWO)))
            throw new ArithmeticException("Invalid n is given");

        num = n.subtract(BigInteger.ONE);

        while (true) {
            if (num.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
                s = s.add(BigInteger.ONE);
                num = num.divide(BigInteger.TWO);
            } else {
                d = num;
                break;
            }
        }

        if (fME(a, d, n).equals(BigInteger.ONE))
            return true;
            
        for (int r = 0; r < s.intValue(); r++) {
            if (fME(a, BigInteger.TWO.pow(r).multiply(d).add(BigInteger.ONE), n).equals(BigInteger.ZERO))
                return false;
        }

        /* for (BigInteger r = BigInteger.ZERO; r.compareTo(s) == -1; r = r.add(BigInteger.ONE))
            if (a.pow(BigInteger.TWO.pow(r.intValue()).multiply(d).intValue()).add(BigInteger.ONE).mod(n).equals(BigInteger.ZERO))
                return true; */

        return false;
    }
}