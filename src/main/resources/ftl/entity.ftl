@Entity
//@Table(name = "TBL_CUSTOMER")
@SequenceGenerator(name = "idGenerator", sequenceName = "SEQ_CUSTOMER", allocationSize = 1)
<#if tableMetaData??>
public class ${tableMetaData.className} extends BaseEntity {
<#else>
is null
</#if>
