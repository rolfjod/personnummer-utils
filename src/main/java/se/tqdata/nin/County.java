/**
 *
 */
package se.tqdata.nin;

/**
 * Enum for counties in Sweden with lower and upperbounds for personal code.
 *
 * @author <a href="mailto:rolf.jo.danielsson@gmail.com">Rolf Danielsson</a>
 */
public enum County {
    STOCKHOLM(001, 139),
    UPPSALA(140, 159),
    SÖDERMANLAND(160, 189),
    ÖSTERGÖTLAND(190, 237),
    JÖNKÖPING(240, 269),
    KRONOBERG(270, 289),
    KALMAR(290, 319),
    GOTLAND(320, 329),
    BLEKINGE(330, 349),
    KRISTIANSTAD(350, 389),
    MALMÖHUS(390, 459),
    HALLAND(460, 479),
    GÖTEBORG_OCH_BOHUS(480, 549),
    ÄLVSBORG(550, 589),
    SKARABORG(590, 619),
    VÄRMLAND(620, 649),
    ÖREBRO(660, 689),
    VÄSTMANLAND(690, 709),
    KOPPARBERG(710, 739),
    GÄVLEBORG(750, 779),
    VÄSTERNORRLAND(780, 819),
    JÄMTLAND(820, 849),
    VÄSTERBOTTEN(850, 889),
    NORRBOTTEN(890, 929),
    ALL(001, 999);

    private final int lower;
    private final int upper;

    County(final int lower, final int upper) {
        this.lower = lower;
        this.upper = upper;
    }

    /**
     * Lower bound for personal code.
     *
     * @return lower bound
     */
    public int lower() {
        return this.lower;
    }

    /**
     * Upper bound for personal code.
     *
     * @return int upper bound
     */
    public int upper() {
        return this.upper;
    }
}
