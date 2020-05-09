package musicalManager;

import jm.music.data.Note;

/**
 * ScaleStrategy which has getNewRandomNote method from each scale.
 * You can create many class that implements this interface as you want.
 * @author Sahatsawat Kanpai
 */
public interface ScaleStrategy {

    /**
     * Get new randomized note by the scale.
     *
     * @return new randomized note
     */
    public Note getNewRandomNote();

    /**
     * Check if the pitch is in the scale or not.
     * @return boolean checked if the pitch is in the scale or not
     */
    public boolean isInScale(PitchManagerEnum pitch);
}
