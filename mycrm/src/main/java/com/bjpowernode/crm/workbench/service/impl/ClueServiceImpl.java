package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.dao.*;
import com.bjpowernode.crm.workbench.domain.*;
import com.bjpowernode.crm.workbench.service.ClueService;

import java.util.List;

/**
 * 线索业务实现类
 *
 * @author:whr 2019/9/2
 */
public class ClueServiceImpl implements ClueService {

    // 线索相关的标
    ClueDao clueDao = SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);
    ClueRemarkDao clueRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ClueRemarkDao.class);
    ClueActivityRelationDao clueActivityRelationDao = SqlSessionUtil.getSqlSession().getMapper(ClueActivityRelationDao.class);

    // 联系人相关表
    ContactsActivityRelationDao contactsActivityRelationDao = SqlSessionUtil.getSqlSession().getMapper(ContactsActivityRelationDao.class);
    ContactsDao contactsDao = SqlSessionUtil.getSqlSession().getMapper(ContactsDao.class);
    ContactsRemarkDao contactsRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ContactsRemarkDao.class);

    // 客户相关表
    CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);
    CustomerRemarkDao customerRemarkDao = SqlSessionUtil.getSqlSession().getMapper(CustomerRemarkDao.class);

    // 交易相关表
    TranDao tranDao = SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    TranHistoryDao tranHistoryDao = SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);

    /**
     * 线索转换方法
     *
     * @param cid
     * @param tran
     * @return
     */
    @Override
    public boolean convert(String cid, Tran tran, String createBy) {
        boolean flag = true;
        // 查询线索相关信息
        Clue clue = clueDao.selectById(cid);
        System.out.println(clue.getDescription());
        /*
            添加客户（公司）
         */
        // 判断这条线索对应的公司是否存在
        Customer customer = customerDao.selectByName(clue.getCompany());
        String sysTime = DateTimeUtil.getSysTime();
        if (customer == null) {
            // 如果公司原本不存在，就为其新建一个
            customer = new Customer();
            customer.setWebsite(clue.getWebsite());
            customer.setName(clue.getCompany());
            customer.setId(UUIDUtil.getUUID());
            customer.setPhone(clue.getPhone());
            customer.setOwner(clue.getOwner());
            customer.setNextContactTime(clue.getNextContactTime());
            customer.setDescription(clue.getDescription());
            customer.setCreateTime(sysTime);
            customer.setCreateBy(createBy);
            customer.setContactSummary(clue.getContactSummary());
            customer.setAddress(clue.getAddress());

            //保存公司
            if (customerDao.save(customer) != 1) {
                flag = false;
            }
        }

        /*
            添加联系人
         */
        Contacts contacts = new Contacts();
        contacts.setId(UUIDUtil.getUUID());
        contacts.setSource(clue.getSource());
        contacts.setOwner(clue.getOwner());
        contacts.setNextContactTime(clue.getNextContactTime());
        contacts.setMphone(clue.getMphone());
        contacts.setJob(clue.getJob());
        contacts.setFullname(clue.getFullname());
        contacts.setEmail(clue.getEmail());
        contacts.setDescription(clue.getDescription());
        contacts.setCustomerId(customer.getId());
        contacts.setCreateTime(sysTime);
        contacts.setCreateBy(createBy);
        contacts.setContactSummary(clue.getContactSummary());
        contacts.setAppellation(clue.getAppellation());
        contacts.setAddress(clue.getAddress());

        if (contactsDao.save(contacts) != 1) {
            flag = false;
        }

        /*
            将该线索关联的备注信息全部查出来
         */
        List<ClueRemark> clueRemarks = clueRemarkDao.selectByCid(clue.getId());

        /*
            将备注转移到客户备注表
         */
        for (ClueRemark clueRemark : clueRemarks) {
            CustomerRemark customerRemark = new CustomerRemark();
            customerRemark.setId(UUIDUtil.getUUID());
            customerRemark.setNoteContent(clueRemark.getNoteContent());
            customerRemark.setCreateBy(createBy);
            customerRemark.setCreateTime(sysTime);
            customerRemark.setCustomerId(customer.getId());
            customerRemark.setEditFlag("0");
            if (customerRemarkDao.save(customerRemark) != 1) {
                flag = false;
            }

            /*
                将备注也转移到联系人备注表里
             */
            ContactsRemark contactsRemark = new ContactsRemark();
            contactsRemark.setId(UUIDUtil.getUUID());
            contactsRemark.setNoteContent(clueRemark.getNoteContent());
            contactsRemark.setCreateBy(createBy);
            contactsRemark.setCreateTime(sysTime);
            contactsRemark.setContactsId(contacts.getId());
            contactsRemark.setEditFlag("0");
            if (contactsRemarkDao.save(contactsRemark) != 1) {
                flag = false;
            }

        }

        /*
            将线索和市场活动关联表里的信息，备份到联系人和市场活动关联表里
         */
        // 先查出线索和市场活动关联表里的信息
        List<ClueActivityRelation> clueActivityRelationList = clueActivityRelationDao.getListByClueId(clue.getId());

        for (ClueActivityRelation relation : clueActivityRelationList) {
            // 遍历关联表中的信息，添加联系人与市场活动的关联关系
            ContactsActivityRelation contactsActivityRelation = new ContactsActivityRelation();
            contactsActivityRelation.setId(UUIDUtil.getUUID());
            contactsActivityRelation.setActivityId(relation.getActivityId());
            contactsActivityRelation.setContactsId(contacts.getId());
            if (contactsActivityRelationDao.save(contactsActivityRelation) != 1) {
                flag = false;
            }
        }

        /*
            如果需要创建交易，就添加一条交易
         */
        if (tran != null) {
            tran.setSource(clue.getSource());
            tran.setOwner(clue.getOwner());
            tran.setNextContactTime(clue.getNextContactTime());
            tran.setDescription(clue.getDescription());
            tran.setCustomerId(customer.getId());
            tran.setContactSummary(clue.getContactSummary());
            tran.setContactsId(contacts.getId());

            if (tranDao.save(tran) != 1) {
                flag = false;
            }

            /*
                创建交易后再添加一条交易记录
             */
            TranHistory tranHistory = new TranHistory();
            tranHistory.setId(UUIDUtil.getUUID());
            tranHistory.setCreateBy(createBy);
            tranHistory.setCreateTime(sysTime);
            tranHistory.setExpectedDate(tran.getExpectedDate());
            tranHistory.setMoney(tran.getMoney());
            tranHistory.setStage(tran.getStage());
            tranHistory.setTranId(tran.getId());
            if (tranHistoryDao.save(tranHistory) != 1) {
                flag = false;
            }
        }

        /*
         最后删除这条线索相关的一切
         */
        // 删除线索备注
        List<ClueRemark> clueRemarkList = clueRemarkDao.selectByCid(clue.getId());
        int num = clueRemarkDao.deleteByCid(clue.getId());
        if (num != clueRemarkList.size()) {
            flag = false;
        }

        // 删除将线索与市场活动的关联数据删除
        List<ClueActivityRelation> listByClueId = clueActivityRelationDao.getListByClueId(clue.getId());
        int num1 = clueActivityRelationDao.deleteByCid(clue.getId());
        if (num1 != listByClueId.size()) {
            flag = false;
        }

        // 删除线索
        int num2 = clueDao.deleteById(clue.getId());
        if (num2 != 1) {
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean saveClud(Clue clue) {
        boolean flag = false;
        try {
            flag = clueDao.saveClud(clue) == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<Clue> getClueList() {
        return clueDao.getClueList();
    }

    @Override
    public Clue detail(String cid) {
        return clueDao.detail(cid);
    }

    @Override
    public List<Activity> getActivityBycid(String cid) {
        return clueDao.getActivityBycid(cid);
    }

    @Override
    public boolean deleteCarById(String id) {
        return contactsActivityRelationDao.deleteCarById(id) == 1;
    }

    @Override
    public boolean bund(String cid, String[] aids) {

        for (String aid : aids) {
            ClueActivityRelation car = new ClueActivityRelation();

            car.setId(UUIDUtil.getUUID());
            car.setClueId(cid);
            car.setActivityId(aid);

            int i = contactsActivityRelationDao.bund(car);
            if (i != 1) {
                return false;
            }
        }
        return true;
    }
}
