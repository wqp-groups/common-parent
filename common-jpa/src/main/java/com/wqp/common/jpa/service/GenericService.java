package com.wqp.common.jpa.service;

import com.wqp.common.jpa.common.PageSearchRequest;
import com.wqp.common.jpa.common.SortCondition;
import com.wqp.common.jpa.domain.GenericDomain;
import com.wqp.common.jpa.repository.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class GenericService<T extends GenericDomain<ID>, ID extends Serializable, S extends Specification<T>, R extends GenericRepository<T, ID>> {

    @Autowired
    protected R repository;


    @Transactional
    public T add(T t){
        t.fillId();
        return this.repository.save(t);
    }

    @Transactional
    public List<T> add(List<T> list){
        List<T> result = Collections.emptyList();
        Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()){
            T next = iterator.next();
            T add = this.add(next);
            result.add(add);
        }
        return result;
    }

    @Transactional
    public T merge(T t){
        return this.add(t);
    }

    @Transactional
    public List<T> merge(List<T> list){
        return this.add(list);
    }

    @Transactional
    public void delete(T t){
        this.repository.delete(t);
    }

    @Transactional
    public void deleteById(ID id){
        this.repository.deleteById(id);
    }

    @Transactional
    public void delete(List<T> list){
        Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()){
            T next = iterator.next();
            this.delete(next);
        }
    }

    public T findOne(T t){
        return this.repository.findOne(Example.of(t)).get();
    }

    public List<T> findAll(T t){
        return this.repository.findAll(Example.of(t));
    }

    public Page<T> findAll(PageSearchRequest<T> psr){
        Sort sort;
        List<SortCondition> sortCondition = psr.getSortCondition();
        if(sortCondition != null && sortCondition.size() > 0){
            List<Sort.Order> collect = sortCondition.stream().map(d -> new Sort.Order(Sort.Direction.fromString(d.getDirection().name()), d.getProperty())).collect(Collectors.toList());
            sort = Sort.by(collect);
        }else {
            sort = getSort();
        }
        return this.repository.findAll(Example.of(psr.getSearchCondition()), PageRequest.of(psr.getPage(), psr.getPageSize(), sort));
    }


    /**
     * 获取Specification<T>对象
     * @param t
     * @return
     */
    private Specification<T> getSpecification(T t){
        S s = null;
        Class<S> clazz = (Class<S>)((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[2];
        try {
            Constructor<S> constructor = clazz.getConstructor(new Class[]{t.getClass()});
            s = constructor.newInstance(new Object[]{t});
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * 获取自定义排序Sort对象
     * @return
     */
    public Sort getSort(){ return null;}

}
