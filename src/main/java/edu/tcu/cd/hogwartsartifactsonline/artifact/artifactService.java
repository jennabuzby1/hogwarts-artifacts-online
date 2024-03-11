package edu.tcu.cd.hogwartsartifactsonline.artifact;

import edu.tcu.cd.hogwartsartifactsonline.artifact.utils.IdWorker;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class artifactService {

    private final artifactRepository artifactRepository;



    private final IdWorker idWorker;

    public artifactService(edu.tcu.cd.hogwartsartifactsonline.artifact.artifactRepository artifactRepository, IdWorker idWorker) {
        this.artifactRepository = artifactRepository;
        this.idWorker = idWorker;
    }



    public artifact findById(String artifactId) {

        return this.artifactRepository.findById(artifactId)
                .orElseThrow(() -> new ArtifactNotFoundException(artifactId));
    }

    public List<artifact> findAll() {
        return this.artifactRepository.findAll();
    }

    public artifact save(artifact newArtifact) {
        newArtifact.setId(idWorker.nextId() + "");
        return this.artifactRepository.save(newArtifact);
    }

    public artifact update(String artifactId, artifact update) {
        return this.artifactRepository.findById(artifactId)
                .map(oldArtifact -> {
                    oldArtifact.setName(update.getName());
                    oldArtifact.setDescription(update.getDescription());
                    oldArtifact.setImageURl(update.getImageURl());
                    return this.artifactRepository.save(oldArtifact);
                })

                .orElseThrow(() ->  new ArtifactNotFoundException(artifactId));
    }

    public void delete(String artifactId){
       this.artifactRepository.findById(artifactId)
                .orElseThrow(() -> new ArtifactNotFoundException(artifactId));
        this.artifactRepository.deleteById(artifactId);
    }

}
