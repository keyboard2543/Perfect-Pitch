package musicalManager;

/**
 * Any Scales in this PerfectPitch test are in here to be used for the test.
 * @author Sahatsawat Kanpai
 */
public enum Scale {
    ChromaticScale(new ChromaticScale()),
    CScale(new CScale()),
    BlackKeys(new BlackKeys()),
    ;

    /** An attribute for any Scale enum. */
    private final ScaleStrategy scaleStrategy;

    /**
     * A constructor for this enum.
     * @param scaleStrategy is scaleStrategy of this Scale
     */
    Scale(ScaleStrategy scaleStrategy) {
        this.scaleStrategy = scaleStrategy;
    }

    /**
     * Get scaleStrategy for this enum.
     * @return scaleStrategy for this enum
     */
    public ScaleStrategy getScaleStrategy() {
        return scaleStrategy;
    }
}
