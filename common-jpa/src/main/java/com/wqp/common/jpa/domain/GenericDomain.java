package com.wqp.common.jpa.domain;

import com.wqp.common.util.common.IDGenerator;
import com.wqp.common.util.common.IdSnowflake;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Timestamp;

@MappedSuperclass
public class GenericDomain<ID extends Serializable>{
    @Id
    @Column(name = "id", nullable = false, length = 50, updatable = false)
    protected ID id;
    @Column(name = "create_uid", nullable = false, updatable = false, length = 50, columnDefinition = "varchar(50) comment '创建者ID'")
    protected String createUid;
    @Column(name = "create_uname", nullable = false, updatable = false, length = 50, columnDefinition = "varchar(50) comment '创建者名称'")
    protected String createUname;
    @Column(name = "create_at", nullable = false, updatable = false, columnDefinition = "datetime(0) comment '创建时间'")
    protected Timestamp createAt;
    @Column(name = "modify_uid", length = 50, columnDefinition = "varchar(50) comment '修改者ID'")
    protected String modifyUid;
    @Column(name = "modify_uname", length = 50, columnDefinition = "varchar(50) comment '修改者名称'")
    protected String modifyUname;
    @Column(name = "modify_at", columnDefinition = "datetime(0) comment '修改时间'")
    protected Timestamp modifyAt;

    public GenericDomain() {
    }

    public void fillId() {
        Field field;
        try {
            field = this.getClass().getDeclaredField("id");
            if (field.getType() == Long.class) {
                this.setId((ID) IdSnowflake.getLocalInstance().nextId(this.getClass()));
            } else {
                this.setId((ID) IDGenerator.uuid());
            }
        } catch (NoSuchFieldException var3) {
            this.setId((ID) IDGenerator.uuid());
        }

    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public String getCreateUid() {
        return createUid;
    }

    public void setCreateUid(String createUid) {
        this.createUid = createUid;
    }

    public String getCreateUname() {
        return createUname;
    }

    public void setCreateUname(String createUname) {
        this.createUname = createUname;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public String getModifyUid() {
        return modifyUid;
    }

    public void setModifyUid(String modifyUid) {
        this.modifyUid = modifyUid;
    }

    public String getModifyUname() {
        return modifyUname;
    }

    public void setModifyUname(String modifyUname) {
        this.modifyUname = modifyUname;
    }

    public Timestamp getModifyAt() {
        return modifyAt;
    }

    public void setModifyAt(Timestamp modifyAt) {
        this.modifyAt = modifyAt;
    }
}
