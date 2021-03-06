/*
 * This file is part of Hopsworks
 * Copyright (C) 2019, Logical Clocks AB. All rights reserved
 *
 * Hopsworks is free software: you can redistribute it and/or modify it under the terms of
 * the GNU Affero General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * Hopsworks is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License along with this program.
 * If not, see <https://www.gnu.org/licenses/>.
 */

package io.hops.hopsworks.common.featurestore.featuregroup;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.hops.hopsworks.common.featurestore.FeaturestoreEntityDTO;
import io.hops.hopsworks.common.featurestore.featuregroup.cached.CachedFeaturegroupDTO;
import io.hops.hopsworks.common.featurestore.featuregroup.ondemand.OnDemandFeaturegroupDTO;
import io.hops.hopsworks.persistence.entity.featurestore.StatisticColumn;
import io.hops.hopsworks.persistence.entity.featurestore.featuregroup.Featuregroup;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO containing the human-readable information of a featuregroup, can be converted to JSON or XML representation
 * using jaxb.
 */
@XmlRootElement
@XmlSeeAlso({CachedFeaturegroupDTO.class, OnDemandFeaturegroupDTO.class})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
    @JsonSubTypes.Type(value = CachedFeaturegroupDTO.class, name = "OnlineFeaturegroupDTO"),
    @JsonSubTypes.Type(value = OnDemandFeaturegroupDTO.class, name = "OnDemandFeaturegroupDTO")})
public class FeaturegroupDTO extends FeaturestoreEntityDTO {

  @XmlElement
  private Boolean descStatsEnabled;
  @XmlElement
  private Boolean featCorrEnabled;
  @XmlElement
  private Boolean featHistEnabled;
  @XmlElement
  private List<String> statisticColumns;

  public FeaturegroupDTO() {
  }

  public FeaturegroupDTO(Featuregroup featuregroup) {
    super(featuregroup.getFeaturestore().getId(), featuregroup.getName(), featuregroup.getCreated(),
        featuregroup.getCreator(), featuregroup.getVersion(), (List) featuregroup.getJobs(),
        featuregroup.getId());
    this.descStatsEnabled = featuregroup.isDescStatsEnabled();
    this.featCorrEnabled = featuregroup.isFeatCorrEnabled();
    this.featHistEnabled = featuregroup.isFeatHistEnabled();
    List<StatisticColumn> statColumns = (List) featuregroup.getStatisticColumns();
    this.statisticColumns = statColumns.stream()
      .map(sc -> sc.getName())
      .collect(Collectors.toList());
  }
  
  public Boolean isDescStatsEnabled() {
    return descStatsEnabled;
  }
  
  public void setDescStatsEnabled(boolean descStatsEnabled) {
    this.descStatsEnabled = descStatsEnabled;
  }
  
  public Boolean isFeatCorrEnabled() {
    return featCorrEnabled;
  }
  
  public void setFeatCorrEnabled(boolean featCorrEnabled) {
    this.featCorrEnabled = featCorrEnabled;
  }
  
  public Boolean isFeatHistEnabled() {
    return featHistEnabled;
  }
  
  public void setFeatHistEnabled(boolean featHistEnabled) {
    this.featHistEnabled = featHistEnabled;
  }
  
  public List<String> getStatisticColumns() {
    return statisticColumns;
  }
  
  public void setStatisticColumns(List<String> statisticColumns) {
    this.statisticColumns = statisticColumns;
  }

  @Override
  public String toString() {
    return "FeaturegroupDTO{" +
      ", descStatsEnabled=" + descStatsEnabled +
      ", featCorrEnabled=" + featCorrEnabled +
      ", featHistEnabled=" + featHistEnabled +
      ", statisticColumns=" + statisticColumns + '\'' +
      '}';
  }
}
