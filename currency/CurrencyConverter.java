package currency;

public class CurrencyConverter {

    public static double convert(double totalIDR, CurrencyType type) {
        double result = totalIDR;

        switch (type) {
            case USD:
                result = totalIDR / 15;
                break;
            case JPY:
                result = totalIDR * 10;
                break;
            case MYR:
                result = totalIDR / 4;
                break;
            case EUR:
                result = totalIDR / 14;
                break;
            default:
                result = totalIDR;
                break;
        }

        return result;
    }

    public static String getCode(CurrencyType type) {
        switch (type) {
            case USD:
                return "USD";
            case JPY:
                return "JPY";
            case MYR:
                return "MYR";
            case EUR:
                return "EUR";
            default:
                return "IDR";
        }
    }
}