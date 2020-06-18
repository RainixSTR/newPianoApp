package com.rainix.controllers;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

import static com.rainix.controllers.ControllerTap.deltaTime;

public class ControllerSound {

    private final int maxTimePlaying = 50000;

    private MidiChannel channel;

    ControllerSound() {
        loadChannel();
    }

    private void loadChannel() {
        try {
            Synthesizer synth = MidiSystem.getSynthesizer();
            synth.open();
            synth.loadInstrument(synth.getDefaultSoundbank().getInstruments()[5]);

            channel = synth.getChannels()[0];

        } catch (MidiUnavailableException e) {
            System.out.println("Cannot get synth");
            e.printStackTrace();
        }
    }

    PlayNote playNote(int numOfNote) {

        PlayNote play = new PlayNote();
        play.setNote(numOfNote);
        play.start();
        return play;
    }

    class PlayNote extends Thread {
        private volatile boolean stop = false;
        private int numOfNote;

        private void setNote(int num) {
            numOfNote = num;
        }

        void setStop() {
            stop = true;
        }

        @Override
        public void run() {
            channel.noteOn(numOfNote, 90);
            int count = 0;
            while (!stop && count < (maxTimePlaying / deltaTime)) {
                count++;
                try {
                    this.sleep(deltaTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            channel.noteOff(numOfNote);
        }
    }
}
