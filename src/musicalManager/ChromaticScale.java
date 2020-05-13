package musicalManager;

import jm.music.data.Note;

import java.util.Random;

/**
 * ChromaticScale ScaleStrategy is scale that references on every musical pitch.
 * @author Sahatsawat Kanpai
 */
public class ChromaticScale implements ScaleStrategy {

    /**
     * Get new randomized note by the scale.
     *
     * @return new randomized note
     */
    @Override
    public Note getNewRandomNote() {
        Random random = new Random();
        int min = 21; // min at 21 (lowest pitch from existing piano named "A")
        int max = 109; // max at 108 + 1 (highest pitch from existing piano named "C")
        int pitchNumber = random.nextInt(max-min) + min;

        Note note = new Note();
        note.setPitch(pitchNumber);
        note.setDuration(5.0);

        return note;
    }

    /**
     * Check if the pitch is in the scale or not.
     *
     * @return boolean checked if the pitch is in the scale or not
     */
    @Override
    public boolean isInScale(PitchManagerEnum pitch) {
        return true;
    }
}
