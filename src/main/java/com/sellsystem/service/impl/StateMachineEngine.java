package com.sellsystem.service.impl;

import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.scxml.SCXMLExecutor;
import org.apache.commons.scxml.TriggerEvent;
import org.apache.commons.scxml.env.SimpleDispatcher;
import org.apache.commons.scxml.env.SimpleErrorHandler;
import org.apache.commons.scxml.env.SimpleErrorReporter;
import org.apache.commons.scxml.env.jexl.JexlContext;
import org.apache.commons.scxml.env.jexl.JexlEvaluator;
import org.apache.commons.scxml.io.SCXMLParser;
import org.apache.commons.scxml.model.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class StateMachineEngine {
    /**
     * 获取状态引擎
     * @param fileName scxml文件名称
     * @param initState 初始状态
     * @return
     */
    private SCXMLExecutor initEngine(String fileName, String initState) {
        SCXMLExecutor engine = new SCXMLExecutor(new JexlEvaluator(),
                new SimpleDispatcher(), new SimpleErrorReporter());
        SCXML stateMachine;
        try {
            URL url = StopWatch.class.getClassLoader().getResource("scxml/"+fileName);
            stateMachine = SCXMLParser.parse(url, new SimpleErrorHandler());
            engine.setSuperStep(true);
            engine.setRootContext(new JexlContext());
            if (!StringUtils.isEmpty(initState)) {
                Map targets = stateMachine.getTargets();
                TransitionTarget tt = (TransitionTarget) targets.get(initState);
                stateMachine.setInitialTarget(tt);
            }
            engine.setStateMachine(stateMachine);
        } catch (IOException | SAXException | ModelException e) {
            e.printStackTrace();
        }
        try {
            engine.go();
        } catch (ModelException me) {
            me.printStackTrace();
        }
        return engine;
    }

    /**
     * 获取转换后的状态
     * @param fileName
     * @param initState
     * @param transformState：转换的事件
     * @return
     */
    public  String getStateByEngine(String fileName,String initState,String transformState){
        String currentStatue = "";
        SCXMLExecutor engine=initEngine(fileName,initState);
        TriggerEvent[] evts = {new TriggerEvent(transformState, TriggerEvent.SIGNAL_EVENT, null)};
        try {
            engine.triggerEvents(evts);
            Object[] states = engine.getCurrentStatus().getStates().toArray();
            if (states.length > 0) {
                currentStatue = ((State)states[0]).getId();
            }
            System.out.println(currentStatue);
        }catch (Exception ex){
            currentStatue="";
        }
        return currentStatue;
    }


    /**
     * 根据初始状态获取 可操作的权限
     * @param fileName
     * @param initState
     * @return
     */
    public  List<String> getCommandsByEngine(String fileName, String initState){
        SCXMLExecutor engine=initEngine(fileName,initState);
        List<String> listCommands=new ArrayList<>();
        try {
            List<Transition> transitionsList = engine.getStateMachine().getInitialTarget().getTransitionsList();
            if (transitionsList.size() > 0) {
                for (Transition t : transitionsList) {
                    listCommands.add(t.getEvent());
                }
            }
        }catch(Exception ex){}
        return listCommands;
    }

    /**
     * 获取初始状态
     * @param fileName
     * @return
     */
    public  String getInitStateByEngine(String fileName){
        SCXMLExecutor engine=initEngine(fileName,"");
        return engine.getStateMachine().getInitial();
    }

}
