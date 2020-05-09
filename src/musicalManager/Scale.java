package musicalManager;

public enum Scale {
    ChromaticScale(new ChromaticScale()),
    CScale(new CScale()),
    BlackKeys(new BlackKeys()),
    ;

    private final ScaleStrategy scaleStrategy;

    Scale(ScaleStrategy scaleStrategy) {
        this.scaleStrategy = scaleStrategy;
    }

    public ScaleStrategy getScaleStrategy() {
        return scaleStrategy;
    }
}
