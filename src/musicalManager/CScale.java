package musicalManager;

import jm.music.data.Note;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class CScale implements ScaleStrategy {

    /**
     * Get new randomized note by the scale.
     *
     * @return new randomized note
     */
    @Override
    public Note getNewRandomNote() {
        Integer[] lowestC = {24, 26, 28, 29, 31, 33, 35};
        List<Integer> cScale = new LinkedList<>(Arrays.asList(lowestC));
        for (int i = 1; i < 7; i++) {
            for (Integer note : lowestC)
                cScale.add(note+12*i);
        }
        cScale.add(21); // Add lowest A that is note in a set of scale C but it is in C scale
        cScale.add(23); // Add lowest B that is note in a set of scale C but it is in C scale
        cScale.add(108); // Add highest C that is note in a set of scale C but it is in C scale

        int noteNum = getRandomFromArray(cScale);
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
        return !pitch.getPitchName().contains("#") && !pitch.getPitchName().contains("b");
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
