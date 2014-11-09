package org.openurp.teach.action

import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.base.Department
import org.openurp.teach.core.Adminclass
import org.openurp.teach.code.StdType
import org.openurp.base.Campus
import org.openurp.teach.core.Major
import org.openurp.base.Teacher
import org.beangle.data.jpa.dao.OqlBuilder
import org.openurp.base.code.Gender
import org.openurp.teach.code.StudyType
import org.openurp.teach.core.Student
import org.beangle.data.model.Entity
import org.openurp.teach.core.Direction

class StudentAction extends RestfulAction[Student] {
  override def editSetting(entity: Student) = {
    val departments = findItems(classOf[Department])
    put("departments", departments)

    val majors = findItems(classOf[Major])
    put("majors", majors)

    val directions = findItems(classOf[Direction])
    put("directions", directions)

    val genders = findItems(classOf[Gender])
    put("genders", genders)

    val majorDeparts = findItems(classOf[Department])
    put("majorDeparts", majorDeparts)

    val type1s = findItems(classOf[StdType])
    put("type1s", type1s)

    val campuse = findItems(classOf[Campus])
    put("campuse", campuse)

    val adminclasses = findItems(classOf[Adminclass])
    put("adminclasses", adminclasses)

    val studyTypes = findItems(classOf[StudyType])
    put("studyTypes", studyTypes)

    val tutors = findItems(classOf[Teacher])
    put("tutors", tutors)

    super.editSetting(entity)
  }

  private def findItems[T <: Entity[_]](clazz: Class[T]): Seq[T] = {
    val query = OqlBuilder.from(clazz)
    query.orderBy("name")
    val items = entityDao.search(query)
    items
  }

}

