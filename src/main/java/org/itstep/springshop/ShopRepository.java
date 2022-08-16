package org.itstep.springshop;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Artist,Long> {

}
