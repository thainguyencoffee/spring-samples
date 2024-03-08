package com.manning.sbip.endpoint;

import com.manning.sbip.model.ReleaseNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
@Endpoint(id = "releaseNotes")
public class ReleaseNotesEndpoint {

    private final Collection<ReleaseNote> releaseNotes;

    @Autowired

    public ReleaseNotesEndpoint(Collection<ReleaseNote> releaseNotes) {
        this.releaseNotes = releaseNotes;
    }

    @ReadOperation
    public Object releaseNotes(@Selector String version) {
        Optional<ReleaseNote> releaseNoteOptional = releaseNotes
                .stream()
                .filter(releaseNote -> version.equals(releaseNote.getVersion()))
                .findFirst();
        if(releaseNoteOptional.isPresent()) {
            return releaseNoteOptional.get();
        }
        return String.format("No such release version exists: %s", version);
    }

    @DeleteOperation
    public void removeReleaseVersion(@Selector String version) {
        Optional<ReleaseNote> releaseNoteOptional = releaseNotes
                .stream()
                .filter(releaseNote -> version.equals(releaseNote.getVersion()))
                .findFirst();
        if (releaseNoteOptional.isPresent()) {
            releaseNotes.remove(releaseNoteOptional.get());
        }
    }
}
