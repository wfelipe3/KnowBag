package knowbag.refactoring;

import org.junit.Test;

import java.util.Date;

/**
 * Created by feliperojas on 10/7/15.
 */
public class ReplaceConstructorWithCreationMethodTest {

    @Test
    public void given_When_Then() {

    }

    private class Loan {
        private final double commitment;
        private final double outstanding;
        private final int riskRating;
        private final Date maturity;
        private final Date expiry;
        private CapitalStrategy capitalStrategy;

        public Loan(double commitment, int riskRating, Date maturity, Date expriry) {
            this(commitment, 0.0, riskRating, maturity, expriry);
        }

        public Loan(double commitment, double outstanding, int riskRating, Date maturity, Date expriry) {
            this(null, commitment, outstanding, riskRating, maturity, expriry);
        }

        public Loan(CapitalStrategy capitalStrategy, double commitment, int riskRating, Date maturity, Date expriry) {
            this(capitalStrategy, commitment, 0.0, riskRating, maturity, expriry);
        }

        public Loan(CapitalStrategy capitalStrategy, double commitment, double outstanding, int riskRating, Date maturity, Date expriry) {
            this.commitment = commitment;
            this.outstanding = outstanding;
            this.riskRating = riskRating;
            this.maturity = maturity;
            this.expiry = expriry;
            this.capitalStrategy = capitalStrategy;

            if (capitalStrategy == null) {
                if (expiry == null)
                    this.capitalStrategy = new CapitalStrategyTermLoan();
                else if (maturity == null)
                    this.capitalStrategy = new CapitalStrategyRevolver();
                else
                    this.capitalStrategy = new CapitalStrategyRCTL();
            }
        }
    }

    private class CapitalStrategy {
    }
}
