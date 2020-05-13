package musicalManager;

import jm.music.data.Note;

/**
 * PitchManagerEnum contains all pitch.
 * Each pitch enum contains pitchName, tested, correct.
 * - pitchName is the name of the pitch.
 * - tested is amount of tested for the pitch.
 * - correct is amount of test correct for the pitch.
 * @author Sahatsawat Kanpai
 */
public enum PitchManagerEnum {
    C(Note.C),
    CSharp(Note.C_SHARP),
    D(Note.D),
    EFlat(Note.E_FLAT),
    E(Note.E),
    F(Note.F),
    FSharp(Note.F_SHARP),
    G(Note.G),
    GSharp(Note.G_SHARP),
    A(Note.A),
    BFlat(Note.B_FLAT),
    B(Note.B),
    ;

    /** PitchName attribute for this Enum. */
    private final String pitchName;
    private int correct = 0;
    private int tested = 0;

    /**
     * Constructor for this Enum.
     * @param pitchName is the name of this pitch
     */
    PitchManagerEnum(String pitchName) {
        this.pitchName = pitchName;
    }

    /**
     * Get the name of this pitch.
     * @return the name of this pitch
     */
    public String getPitchName() {
        return pitchName;
    }

    /**
     * Get amount of time that user has tested this pitch.
     * @return amount of time that user has tested this pitch
     */
    public int getTested() {
        return tested;
    }

    /**
     * Set the tested time.
     * @param tested is tested time to set
     */
    public void setTested(int tested) {
        this.tested = tested;
    }

    /**
     * Get amount of time that user determined this pitch correctly.
     * @return amount of time that user determined this pitch correctly
     */
    public int getCorrect() {
        return correct;
    }

    /**
     * Set the amount of correct.
     * @param correct is correct amount to set
     */
    public void setCorrect(int correct) {
        this.correct = correct;
    }

    /**
     * Get amount of time that user has tested every pitch.
     * @return amount of time that user has tested every pitch
     */
    public static int getAllTested() {
        int result = 0;
        for (PitchManagerEnum pitch : PitchManagerEnum.values())
            result += pitch.getTested();
        return result;
    }

    /**
     * Get amount of time that user has correct tested every pitch.
     * @return amount of time that user has correct tested every pitch
     */
    public static int getAllCorrect() {
        int result = 0;
        for (PitchManagerEnum pitch : PitchManagerEnum.values())
            result += pitch.getCorrect();
        return result;
    }

    /**
     * Method to be used when this pitch has been tested each time and get correct.
     * Add tested and correct value by 1.
     */
    public void testedAndCorrect() {
        tested += 1;
        correct += 1;
    }

    /**
     * Method to be used when this pitch has been tested each time and get incorrect.
     * Add tested value by 1.
     */
    public void testedAndIncorrect() {
        tested += 1;
    }

    /**
     * Get accurate by a percentage value.
     * @return accurate by a percentage value
     */
    public double getAccuracy() {
        if (tested == 0) return 0.0;
        return (double) correct/tested * 100;
    }

    /**
     * Get accurate result of every tested pitch.
     * @return accurate result of every tested pitch
     */
    public static double getAllAccurate() {
        return (double) PitchManagerEnum.getAllCorrect()/PitchManagerEnum.getAllTested() * 100;
    }

    /**
     * Check if this pitch enum is tested.
     * @return boolean from checking if is tested
     */
    public boolean isTested(){
        return tested > 0;
    }

    /**
     * Reset tested and correct value of this pitch enum to 0.
     */
    public void reset() {
        tested = 0;
        correct = 0;
    }

    /**
     * Reset tested and correct value of all pitch enum to 0.
     */
    public static void resetAll() {
        for (PitchManagerEnum pitch : PitchManagerEnum.values())
            pitch.reset();
    }

    /**
     * Find and Return pitch by a note.
     * @param note is the note to find for pitch
     * @return pitch of the note
     */
    public static PitchManagerEnum getPitchByNote(Note note) {
        for (PitchManagerEnum pitch : PitchManagerEnum.values()) {
            if (note.getName().equals(pitch.getPitchName()))
                return pitch;
        }
        return FSharp;
    }
}
