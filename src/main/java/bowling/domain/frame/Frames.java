package bowling.domain.frame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Frames {

    public static final int MAX_FRAMES_SIZE = 10;

    private final List<Frame> frames = new ArrayList<>();

    public Frames(int pinNo) {
        frames.add(NormalFrame.init(pinNo));
    }

    public void addPin(int pinNo) {
        if (isFinished()) {
            throw new IllegalStateException("can't add more pins");
        }

        Frame lastFrame = getLastFrame();
        if (lastFrame.canGetScore()) {
            frames.add(lastFrame.nextFrame(pinNo));
            return;
        }

        lastFrame.addPin(pinNo);
     }

    public boolean isFinished() {
        return frames.size() == MAX_FRAMES_SIZE && getLastFrame().canGetScore();
    }

    private Frame getLastFrame() {
        return frames.get(frames.size() - 1);
    }

    public int currentFrame() {
        return getLastFrame().canGetScore()
                ? frames.size() + 1
                : frames.size();
    }

    public List<Frame> getFrames() {
        return Collections.unmodifiableList(frames);
    }

    public List<Integer> getScores() {
        return frames.stream()
                .map(Frame::score)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

}
