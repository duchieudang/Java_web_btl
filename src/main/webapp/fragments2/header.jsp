<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="container-fluid bg-dark px-0">
    <div class="row gx-0">
        <div class="col-lg-3 bg-dark d-none d-lg-block">
            <a href="index.jsp" class="navbar-brand w-100 h-100 m-0 p-0 d-flex align-items-center justify-content-center">
                <h1 class="m-0 text-primary text-uppercase">Grand 
                <span class="d-block">Palace</span></h1>
                
            </a>
        </div>
        <div class="col-lg-9">
            <div class="row gx-0 bg-white d-none d-lg-flex">
                <div class="col-lg-7 px-5 text-start">
                    <div class="h-100 d-inline-flex align-items-center py-2 me-4">
                        <i class="fa fa-envelope text-primary me-2"></i>
                        <p class="mb-0">admin@khachsana.com</p>
                    </div>
                    <div class="h-100 d-inline-flex align-items-center py-2">
                        <i class="fa fa-phone-alt text-primary me-2"></i>
                        <p class="mb-0">+(84) 123 836 8597</p>
                    </div>
                </div>
                <div class="col-lg-5 px-5 text-end">
                    <div class="d-inline-flex align-items-center py-2">
                        <a class="me-3" href="" title="Khách sạn A trên facebook"><i class="fab fa-facebook-f"></i></a>
                        <a class="me-3" href="" title="Khách sạn A trên twitter"><i class="fab fa-twitter"></i></a>
                        <a class="me-3" href="" title="Tuyển dụng nhân viên 24/7"><i class="fab fa-linkedin-in"></i></a>
                        <a class="me-3" href="" title="Khách sạn A trên instagram"><i class="fab fa-instagram"></i></a>
                        <a class="" href=""><i class="fab fa-youtube"></i></a>
                    </div>
                </div>
            </div>
            <nav class="navbar navbar-expand-lg bg-dark navbar-dark p-3 p-lg-0">
                <a href="index.jsp" class="navbar-brand d-block d-lg-none">
                    <h1 class="m-0 text-primary text-uppercase">Grand</h1>
                </a>
                <button type="button" class="navbar-toggler" data-bs-toggle="collapse" data-bs-target="#navbarCollapse">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarCollapse">
                    <div class="navbar-nav mr-auto py-0">
                        <a href="index.jsp" class="nav-item nav-link active">Trang chủ</a>
                        <a href="${pageContext.request.contextPath}/roomforclient" class="nav-item nav-link">Phòng</a>
                           <a href="${pageContext.request.contextPath}/phong-list" class="nav-item nav-link">Phòng Theo Yêu Cầu</a>
                        <div class="nav-item dropdown">
                            <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown">Khác</a>
                            <div class="dropdown-menu rounded-0 m-0">
                                <a href="index.jsp#about" class="dropdown-item">Về khách sạn</a>
                                <a href="index.jsp#services" class="dropdown-item">Dịch vụ</a>
                                <a href="index.jsp#testimonial" class="dropdown-item">Đánh giá</a>
                                <a href="index.jsp#about-dev" class="dropdown-item">Về chúng tôi</a>
                                <a href="index.jsp#footer" class="dropdown-item">Liên lạc</a>
                            </div>
                        </div>
                        
                        <style>
                        	#searchform {
                        		height: 80px; 
                        	}
                        </style>
                        
                        
                        
                    </div>
                    <form id="searchform" class="d-flex align-items-center w-50 mr-auto py-0 gap-3" method="get" action="${pageContext.request.contextPath}/phong-list-3">
                        	<input type="text" name="uuid" class="form-control" placeholder="Nhập mã phòng, sdt,cccd hoặc email tại đây..."></input>
                        	<button type="submit" class="btn btn-sm btn-light rounded">Tìm</button>
                        </form>
<!--                     <div> -->
<!--                         <a href="register.jsp" class="btn btn-primary rounded-0 py-4 px-md-5">Đăng ký</a> -->
<!--                         <a href="login.jsp" class="btn btn-primary rounded-0 py-4 px-md-5">Đăng nhập</a> -->
<!--                     </div> -->
                </div>
            </nav>
        </div>
    </div>
</div>