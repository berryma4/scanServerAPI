package edu.msu.frib.scanserver.common;

/**
 * Created with IntelliJ IDEA.
 * User: frederick
 * Date: 5/30/13
 * Time: 8:58 PM
 * To change this template use File | Settings | File Templates.
 */
public enum Comparison
{
    /** Value is at desired value, within tolerance */
    EQUALS("="),

    /** Value above desired value, '>' */
    ABOVE(">"),

    /** Value at or above desired value, '>=' */
    AT_LEAST(">="),

    /** Value below desired value, '<' */
    BELOW("<"),

    /** Value at or below desired value, '<=' */
    AT_MOST("<="),

    /** Value has increased by some amount */
    INCREASE_BY("to increase by"),

    /** Value has decreased by some amount */
    DECREASE_BY("to decrease by");

    final private String label;

    /** Initialize
     *  @param label
     */
    private Comparison(final String label)
    {
        this.label = label;
    }

    /** @return Label, i.e. text representation meant for GUI
     *  @see #name()
     */
    @Override
    public String toString()
    {
        return label;
    }
}
