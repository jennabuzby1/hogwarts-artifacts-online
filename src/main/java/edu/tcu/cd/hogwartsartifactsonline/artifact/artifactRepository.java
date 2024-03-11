package edu.tcu.cd.hogwartsartifactsonline.artifact;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface artifactRepository extends JpaRepository<artifact, String> {

}
