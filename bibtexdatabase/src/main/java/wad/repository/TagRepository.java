
package wad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wad.domain.Tag;

public interface TagRepository extends JpaRepository<Tag, Long>{
    
    Tag findByName(String name);
    
}
