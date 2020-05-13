package musicalManager;

import jm.music.data.Note;

import java.util.*;

/**
 * BlankKeys ScaleStrategy that in scale has only black keys pitch references on piano.
 * @author Sahatsawat Kanpai
 */
public class BlackKeys implements ScaleStrategy {
    /**
     * Get new randomized note by the scale.
     *
     * @return new randomized note
     */
    @Override
    public Note getNewRandomNote() {
        Integer[] lowestBlackKeys = {25, 27, 30, 32, 34};
        List<Integer> blackKeyScale = new LinkedList<>(Arrays.asList(lowestBlackKeys));
        for (int i = 1; i < 7; i++) {
            for (Integer note : lowestBlackKeys) {
                blackKeyScale.add(note + 12 * i);
            }
        }
        blackKeyScale.add(22); // Add lowest Bb that is note in a set of Black keys but it is in Black keys

        int noteNum = getRandomFromArray(blackKeyScale);
        Note note = new Note();
        note.setPitch(noteNum);
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
        return pitch.getPitchName().contains("#") || pitch.getPitchName().contains("b");
    }

    /**
     * Get randomized element in an arrayList.
     * @param arrayList is arrayList to be randomized element in its.
     * @return and Integer
     */
    public static Integer getRandomFromArray(List<Integer> arrayList) {
        int rnd = new Random().nextInt(arrayList.size());
        return arrayList.get(rnd);
    }
}
