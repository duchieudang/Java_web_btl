<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <title>Khách sạn Grand Palace</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="" name="keywords">
    <meta content="" name="description">
    <%@ include file="fragments2/include-css.jsp" %>
</head>

<body>
    <div class="container-xxl bg-white p-0">
        <%@ include file="fragments2/spinner.jsp" %>
        <%@ include file="fragments2/header.jsp" %>
	
	
	

	
	
	<div class="container-xxl py-5 wow fadeInUp" data-wow-delay="0.1s" id="rooms">
    <div class="container">
        <div class="text-center wow fadeInUp" data-wow-delay="0.1s">
            <h6 class="section-title text-center text-primary text-uppercase">Phòng của chúng tôi</h6>
            <h1 class="mb-5">Khám phá các phòng tại khách sạn của chúng tôi</h1>
        </div>
        <div class="d-flex align-items-center justify-content-start gap-2 my-3">
	    <h5 class="mb-0">Bảng các phòng</h5>
	    <select class="form-select w-auto" aria-label="Page selection" id="pageSelect">
	        <c:forEach var="page" begin="1" end="${totalPages}">
	            <option value="${page}" ${page == currentPage ? 'selected' : ''}>Trang ${page}</option>
	        </c:forEach>
	    </select>
	    <script>
		    document.addEventListener('DOMContentLoaded', () => {
		        const selectElement = document.getElementById('pageSelect');
		        const baseUrl = `${pageContext.request.contextPath}/roomforclient?page=`;
		        selectElement.addEventListener('change', (event) => {
		            const selectedPage = event.target.value;
		            if (selectedPage) {
		                window.location.href = baseUrl + selectedPage;
		            }
		        });
		    });
		</script>
		<p class="mb-0">trên tổng số ${totalPages} trang.</p>		
		<p class="mb-0">Tổng số phòng: ${totalItems} </p>				    
	</div>
        <div class="row g-4" id="room-data">
       <c:forEach var="row" items="${items}">
       <div class="col-lg-4 col-md-6 wow fadeInUp" data-wow-delay="0.1s">
                <div class="room-item shadow rounded overflow-hidden">
                    <div class="position-relative">
                      <a href="${pageContext.request.contextPath}/bookingforclient?room_id=${row.roomId}">
  <img
    style="width: 100%; height: auto; aspect-ratio: 16 / 9; object-fit: cover;"
    src="data:image/jpeg;base64,${row.roomImageBase64}"
    alt="${row.roomName}"
  >
</a>

                        <small class="position-absolute start-0 top-100 translate-middle-y bg-primary text-white rounded py-1 px-3 ms-4">${row.roomPricePerHourVnd }₫/đêm </small>
                    </div>
                    <div class="p-4 mt-2">
                        <div class="d-flex justify-content-between mb-3">
                            <h5 class="mb-0">${row.roomName }</h5>
                            <div class="ps-2">
                            <c:forEach var="abc" begin="1" end="${row.roomStarCount}">
					            <small class="fa fa-star text-primary"></small>
					        </c:forEach>
                            </div>
                        </div>
                        <div class="d-flex mb-3">
                            <small class="border-end me-3 pe-3"><i class="fa fa-bed text-primary me-2"></i>${row.roomBedCount } giường</small>
                            <small class="border-end me-3 pe-3"><i class="fa fa-bed text-primary me-2"></i>${row.roomSize } m<sup>2</sup></small>
                        </div>
                        <p class="text-body mb-3">${row.roomNote }</p>
                        <div class="d-flex justify-content-between">
                            
                            <a class="btn btn-sm btn-dark rounded py-2 px-4" href="${pageContext.request.contextPath}/bookingforclient?room_id=${row.roomId}">Đặt phòng</a>
                        </div>
                    </div>
                </div>
            </div>
       </c:forEach>
<!--             bắt đầu 1 phòng -->
            
<!--             kết thúc 1 phòng -->
        </div>
    </div>
</div>

<%@ include file="fragments2/footer.jsp" %>

        <!-- <a href="#" class="btn btn-lg btn-primary btn-lg-square back-to-top"><i class="bi bi-arrow-up"></i></a> -->
<!--         <a href="#" class="btn btn-lg btn-primary btn-lg-square"><i class="bi bi-arrow-up"></i></a> -->
    </div>
    <%@ include file="fragments2/include-js.jsp" %>
</body>
</html>