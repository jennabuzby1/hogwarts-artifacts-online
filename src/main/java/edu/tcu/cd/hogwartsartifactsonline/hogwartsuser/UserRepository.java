package edu.tcu.cd.hogwartsartifactsonline.hogwartsuser;

import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<HogwartsUser, Integer> {
}