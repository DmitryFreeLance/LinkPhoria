package ru.linkphoria.linkphoriaproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.linkphoria.linkphoriaproject.models.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}