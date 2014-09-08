/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.grubmenow.service;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SearchQuery implements org.apache.thrift.TBase<SearchQuery, SearchQuery._Fields>, java.io.Serializable, Cloneable, Comparable<SearchQuery> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("SearchQuery");

  private static final org.apache.thrift.protocol.TField ZIPCODE_FIELD_DESC = new org.apache.thrift.protocol.TField("zipcode", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField RADIUS_FIELD_DESC = new org.apache.thrift.protocol.TField("radius", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField AVAILABLE_DAY_FIELD_DESC = new org.apache.thrift.protocol.TField("availableDay", org.apache.thrift.protocol.TType.STRING, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new SearchQueryStandardSchemeFactory());
    schemes.put(TupleScheme.class, new SearchQueryTupleSchemeFactory());
  }

  public String zipcode; // required
  public int radius; // required
  public String availableDay; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ZIPCODE((short)1, "zipcode"),
    RADIUS((short)2, "radius"),
    AVAILABLE_DAY((short)3, "availableDay");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // ZIPCODE
          return ZIPCODE;
        case 2: // RADIUS
          return RADIUS;
        case 3: // AVAILABLE_DAY
          return AVAILABLE_DAY;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __RADIUS_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ZIPCODE, new org.apache.thrift.meta_data.FieldMetaData("zipcode", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING        , "String")));
    tmpMap.put(_Fields.RADIUS, new org.apache.thrift.meta_data.FieldMetaData("radius", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32        , "Integer")));
    tmpMap.put(_Fields.AVAILABLE_DAY, new org.apache.thrift.meta_data.FieldMetaData("availableDay", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING        , "String")));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(SearchQuery.class, metaDataMap);
  }

  public SearchQuery() {
    this.radius = 5;

  }

  public SearchQuery(
    String zipcode,
    int radius,
    String availableDay)
  {
    this();
    this.zipcode = zipcode;
    this.radius = radius;
    setRadiusIsSet(true);
    this.availableDay = availableDay;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public SearchQuery(SearchQuery other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetZipcode()) {
      this.zipcode = other.zipcode;
    }
    this.radius = other.radius;
    if (other.isSetAvailableDay()) {
      this.availableDay = other.availableDay;
    }
  }

  public SearchQuery deepCopy() {
    return new SearchQuery(this);
  }

  @Override
  public void clear() {
    this.zipcode = null;
    this.radius = 5;

    this.availableDay = null;
  }

  public String getZipcode() {
    return this.zipcode;
  }

  public SearchQuery setZipcode(String zipcode) {
    this.zipcode = zipcode;
    return this;
  }

  public void unsetZipcode() {
    this.zipcode = null;
  }

  /** Returns true if field zipcode is set (has been assigned a value) and false otherwise */
  public boolean isSetZipcode() {
    return this.zipcode != null;
  }

  public void setZipcodeIsSet(boolean value) {
    if (!value) {
      this.zipcode = null;
    }
  }

  public int getRadius() {
    return this.radius;
  }

  public SearchQuery setRadius(int radius) {
    this.radius = radius;
    setRadiusIsSet(true);
    return this;
  }

  public void unsetRadius() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __RADIUS_ISSET_ID);
  }

  /** Returns true if field radius is set (has been assigned a value) and false otherwise */
  public boolean isSetRadius() {
    return EncodingUtils.testBit(__isset_bitfield, __RADIUS_ISSET_ID);
  }

  public void setRadiusIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __RADIUS_ISSET_ID, value);
  }

  public String getAvailableDay() {
    return this.availableDay;
  }

  public SearchQuery setAvailableDay(String availableDay) {
    this.availableDay = availableDay;
    return this;
  }

  public void unsetAvailableDay() {
    this.availableDay = null;
  }

  /** Returns true if field availableDay is set (has been assigned a value) and false otherwise */
  public boolean isSetAvailableDay() {
    return this.availableDay != null;
  }

  public void setAvailableDayIsSet(boolean value) {
    if (!value) {
      this.availableDay = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ZIPCODE:
      if (value == null) {
        unsetZipcode();
      } else {
        setZipcode((String)value);
      }
      break;

    case RADIUS:
      if (value == null) {
        unsetRadius();
      } else {
        setRadius((Integer)value);
      }
      break;

    case AVAILABLE_DAY:
      if (value == null) {
        unsetAvailableDay();
      } else {
        setAvailableDay((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ZIPCODE:
      return getZipcode();

    case RADIUS:
      return Integer.valueOf(getRadius());

    case AVAILABLE_DAY:
      return getAvailableDay();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case ZIPCODE:
      return isSetZipcode();
    case RADIUS:
      return isSetRadius();
    case AVAILABLE_DAY:
      return isSetAvailableDay();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof SearchQuery)
      return this.equals((SearchQuery)that);
    return false;
  }

  public boolean equals(SearchQuery that) {
    if (that == null)
      return false;

    boolean this_present_zipcode = true && this.isSetZipcode();
    boolean that_present_zipcode = true && that.isSetZipcode();
    if (this_present_zipcode || that_present_zipcode) {
      if (!(this_present_zipcode && that_present_zipcode))
        return false;
      if (!this.zipcode.equals(that.zipcode))
        return false;
    }

    boolean this_present_radius = true;
    boolean that_present_radius = true;
    if (this_present_radius || that_present_radius) {
      if (!(this_present_radius && that_present_radius))
        return false;
      if (this.radius != that.radius)
        return false;
    }

    boolean this_present_availableDay = true && this.isSetAvailableDay();
    boolean that_present_availableDay = true && that.isSetAvailableDay();
    if (this_present_availableDay || that_present_availableDay) {
      if (!(this_present_availableDay && that_present_availableDay))
        return false;
      if (!this.availableDay.equals(that.availableDay))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(SearchQuery other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetZipcode()).compareTo(other.isSetZipcode());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetZipcode()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.zipcode, other.zipcode);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetRadius()).compareTo(other.isSetRadius());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRadius()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.radius, other.radius);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetAvailableDay()).compareTo(other.isSetAvailableDay());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAvailableDay()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.availableDay, other.availableDay);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("SearchQuery(");
    boolean first = true;

    sb.append("zipcode:");
    if (this.zipcode == null) {
      sb.append("null");
    } else {
      sb.append(this.zipcode);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("radius:");
    sb.append(this.radius);
    first = false;
    if (!first) sb.append(", ");
    sb.append("availableDay:");
    if (this.availableDay == null) {
      sb.append("null");
    } else {
      sb.append(this.availableDay);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class SearchQueryStandardSchemeFactory implements SchemeFactory {
    public SearchQueryStandardScheme getScheme() {
      return new SearchQueryStandardScheme();
    }
  }

  private static class SearchQueryStandardScheme extends StandardScheme<SearchQuery> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, SearchQuery struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ZIPCODE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.zipcode = iprot.readString();
              struct.setZipcodeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // RADIUS
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.radius = iprot.readI32();
              struct.setRadiusIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // AVAILABLE_DAY
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.availableDay = iprot.readString();
              struct.setAvailableDayIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, SearchQuery struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.zipcode != null) {
        oprot.writeFieldBegin(ZIPCODE_FIELD_DESC);
        oprot.writeString(struct.zipcode);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(RADIUS_FIELD_DESC);
      oprot.writeI32(struct.radius);
      oprot.writeFieldEnd();
      if (struct.availableDay != null) {
        oprot.writeFieldBegin(AVAILABLE_DAY_FIELD_DESC);
        oprot.writeString(struct.availableDay);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class SearchQueryTupleSchemeFactory implements SchemeFactory {
    public SearchQueryTupleScheme getScheme() {
      return new SearchQueryTupleScheme();
    }
  }

  private static class SearchQueryTupleScheme extends TupleScheme<SearchQuery> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, SearchQuery struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetZipcode()) {
        optionals.set(0);
      }
      if (struct.isSetRadius()) {
        optionals.set(1);
      }
      if (struct.isSetAvailableDay()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetZipcode()) {
        oprot.writeString(struct.zipcode);
      }
      if (struct.isSetRadius()) {
        oprot.writeI32(struct.radius);
      }
      if (struct.isSetAvailableDay()) {
        oprot.writeString(struct.availableDay);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, SearchQuery struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.zipcode = iprot.readString();
        struct.setZipcodeIsSet(true);
      }
      if (incoming.get(1)) {
        struct.radius = iprot.readI32();
        struct.setRadiusIsSet(true);
      }
      if (incoming.get(2)) {
        struct.availableDay = iprot.readString();
        struct.setAvailableDayIsSet(true);
      }
    }
  }

}

