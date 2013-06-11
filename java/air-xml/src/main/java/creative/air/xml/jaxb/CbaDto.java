package creative.air.xml.jaxb;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * 
 * @author
 * Eric Han feuyeux@gmail.com
 * 04/08/2012
 * @since  0.0.1
 * @version 0.0.1
 */
@XmlRootElement(name = "cba")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class CbaDto {
	private Integer id;
	private String name;
	private String value;

	public CbaDto() {

	}

	public CbaDto(Integer id, String name, String value) {
		super();
		this.id = id;
		this.name = name;
		this.value = value;
	}

	@XmlElement
	public Integer getId() {
		return id;
	}

	@XmlElement
	public String getName() {
		return name;
	}

	@XmlElement
	public String getValue() {
		return value;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return this.getClass().getName() + "[" + "id = " + id + ", " + "name = " + name + ", " + "value = " + value + "]";
	}
}
