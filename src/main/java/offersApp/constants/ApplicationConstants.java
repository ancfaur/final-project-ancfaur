package offersApp.constants;

public class ApplicationConstants {
    public static class Roles {
        public static final String ADMINISTRATOR = "administrator";
        public static final String AGENT = "agent";
        public static final String CUSTOMER= "customer";
        public static final String[] ROLES = new String[] {ADMINISTRATOR, AGENT, CUSTOMER};
    }

    public static class Categories {
        public static final String CULTURAL = "cultural activities";
        public static final String EDUCATIONAL = "educational activities";
        public static final String MUSICAL = "musical activities";
        public static final String PHYSICAL = "physical activities";
        public static final String NIGHTLIFE = "nightlife and parties";
        public static final String ACCOMODATION = "accomodation";
        public static final String CULINAR = "culinar activities";
        public static final String FAMILY = "family activities";
        public static final String GROUP = "group actitities";
        public static final String ROMANTIC = "couple activities";
        public static final String ALL = "ALL";
        public static final String[] CATEGORIES = new String[] {CULTURAL, EDUCATIONAL, MUSICAL, PHYSICAL, NIGHTLIFE, ACCOMODATION, CULINAR, FAMILY, GROUP, ROMANTIC};
    }
    public static class Ordering {
        public static final String PRICE = "price";
        public static final String NO_STARS = "number stars";
        public static final String PERCENT_DISCOUNT = "percentage discount";
        public static final String DATE = "DATE";
        public static final String[] ORDERINGS = new String[] {PRICE, NO_STARS, PERCENT_DISCOUNT};
    }

}