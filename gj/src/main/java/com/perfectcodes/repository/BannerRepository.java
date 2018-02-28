package com.perfectcodes.repository;

import com.perfectcodes.domain.Banner;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface BannerRepository extends CrudRepository<Banner,Long>,JpaSpecificationExecutor<Banner>{

}
