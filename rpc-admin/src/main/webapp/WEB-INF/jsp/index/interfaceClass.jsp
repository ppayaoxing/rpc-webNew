<%@ page language="java" contentType="text/html; charset=utf-8"   pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>监控中心</title>
    <meta name="keywords" content="监控中心">
    <meta name="description" content="监控中心">
    <link rel="shortcut icon" href="favicon.ico"> <link href="../../css/bootstrap.min.css" rel="stylesheet">
    <link href="../../css/font-awesome.min.css" rel="stylesheet">
    <!-- Morris -->
    <link href="../../css/plugins/morris/morris-0.4.3.min.css" rel="stylesheet">
    <!-- Gritter -->
    <link href="../../js/plugins/gritter/jquery.gritter.css" rel="stylesheet">
    <link href="../../css/animate.min.css" rel="stylesheet">
    <link href="../../css/style.min.css" rel="stylesheet"><base target="_blank">

</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>列表</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                            <a class="close-link">
                                <i class="fa fa-times"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th>接口</th>
                                    <th>实现服务</th>
                                </tr>
                            </thead>
                             <tbody>
								<c:forEach items="${data}" var="dt" varStatus="idx">
									    <tr>
									       <td>${idx.index + 1}</td>
		                                    <td>${dt.key}</td>
		                                    <td>${dt.value}</td>
		                                </tr>
								</c:forEach>
                            </tbody>
                        </table>

                    </div>
                </div>
            </div>
       </div>
    </div>
</body>

</html>