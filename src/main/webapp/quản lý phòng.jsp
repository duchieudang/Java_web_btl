<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="utf-8">
	<meta content="width=device-width, initial-scale=1.0" name="viewport">
	
	<title>Quản lý phòng</title>
	
	<!-- Favicons -->
	<link href="assets/img/favicon.png" rel="icon">
	<link href="assets/img/apple-touch-icon.png" rel="apple-touch-icon">
	
	<!-- Google Fonts -->
	<link href="https://fonts.gstatic.com" rel="preconnect">
	<link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">
	
	<!-- Vendor CSS Files -->
	<link href="assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
	<link href="assets/vendor/boxicons/css/boxicons.min.css" rel="stylesheet">
	<link href="assets/vendor/quill/quill.snow.css" rel="stylesheet">
	<link href="assets/vendor/quill/quill.bubble.css" rel="stylesheet">
	<link href="assets/vendor/remixicon/remixicon.css" rel="stylesheet">
	<link href="assets/vendor/simple-datatables/style.css" rel="stylesheet">
  
	 <!-- Template Main CSS File -->
	 <link href="assets/css/style.css" rel="stylesheet">
	 
 	<script>
	    function toggleInput(checkboxId, inputId) {
	        const checkbox = document.getElementById(checkboxId);
	        const input = document.getElementById(inputId);
	        input.disabled = !checkbox.checked;
	    }
	</script>
	
</head>

<body>

	<%@ include file="fragments/header.jsp" %>
	<%@ include file="fragments/sidebar.jsp" %>
	<main id="main" class="main">
		<div class="pagetitle">
			<h1>Quản lý phòng</h1>
			<nav>
				<ol class="breadcrumb">
				<li class="breadcrumb-item"><a href="index.html">Trang chủ</a></li>
				<li class="breadcrumb-item">Quản lý</li>
				<li class="breadcrumb-item active">Phòng</li>
			</ol>
			</nav>
		</div>
		<section class="section">
			<div class="row">
				<div class="col-lg-12">
					<div class="card">
        				<div class="card-body">
        				<div class="d-flex align-items-center justify-content-start gap-2 my-3">
						    <h5 class="mb-0">Bảng các phòng</h5>
						    <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#addPhongModal">
						        Thêm
						    </button>
						    <select class="form-select w-auto" aria-label="Page selection" id="pageSelect">
						        <c:forEach var="page" begin="1" end="${totalPages}">
						            <option value="${page}" ${page == currentPage ? 'selected' : ''}>Trang ${page}</option>
						        </c:forEach>
						    </select>
						    <script>
							    document.addEventListener('DOMContentLoaded', () => {
							        const selectElement = document.getElementById('pageSelect');
							        const baseUrl = `${pageContext.request.contextPath}/room?page=`;
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
						        				
						<c:if test="${flag == 'fail'}">
<%-- 					        <p style="z-index: 999999" class="error">${message}</p> --%>
					        <span  style="z-index: 999999" class="alert alert-danger bg-danger text-light border-0 alert-dismissible fade show position-fixed bottom-0 end-0 m-3" role="alert">
                				${message}
                				<button type="button" class="btn-close btn-close-white" data-bs-dismiss="alert" aria-label="Close"></button>
              				</span>
					    </c:if>
					    <c:if test="${flag == 'ok'}">
<%-- 					        <p class="success">${message}</p> --%>
					        <span  style="z-index: 999999" class="alert alert-success bg-success text-light border-0 alert-dismissible fade show position-fixed bottom-0 end-0 m-3" role="alert">
							    ${message}
							    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="alert" aria-label="Close"></button>
							</span>
					    </c:if>  				
<%--           				<c:if test="${not empty success}"> --%>
<!-- 						    <span class="alert alert-success bg-success text-light border-0 alert-dismissible fade show position-fixed bottom-0 end-0 m-3" role="alert"> -->
<%-- 							    ${success} --%>
<!-- 							    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="alert" aria-label="Close"></button> -->
<!-- 							</span> -->
<%-- 						</c:if> --%>
<%-- 						<c:if test="${not empty failure}"> --%>
<!-- 						    <span class="alert alert-danger bg-danger text-light border-0 alert-dismissible fade show position-fixed bottom-0 end-0 m-3" role="alert"> -->
<%--                 				${failure} --%>
<!--                 				<button type="button" class="btn-close btn-close-white" data-bs-dismiss="alert" aria-label="Close"></button> -->
<!--               				</span> -->
<%-- 						</c:if> --%>
						<table class="table datatable">
  							<thead>
								<tr>
									<th scope="col" class="align-middle">ID</th>
									<th scope="col" class="align-middle">Tên</th>
									<th scope="col" class="align-middle">Ảnh</th>
									<th scope="col">Diện tích (m<sup>2</sup>)</th>
									<th scope="col">Số giường</th>
									<th scope="col">Số sao</th>
									<th scope="col">Giá tiền (₫/đêm)</th>
									<th scope="col">Khả dụng</th>
									<th scope="col">Ghi chú</th>
									<th scope="col">Hành động</th>
								</tr>
							</thead>
  							<tbody>
  							<c:forEach var="row" items="${items}">
									<tr>
									    <td class="align-middle">${row.roomId}</td>
									    <td class="align-middle">${row.roomName}</td>
									    <td class="align-middle">
									        <c:choose>
									            <c:when test="${not empty row.getRoomImageBase64()}"> <!-- i got error -->
									                <img src="data:image/jpeg;base64,${row.getRoomImageBase64()}" alt="Room Image" style="width:160px;height:90px;">
									            </c:when>
									            <c:otherwise>
									                <span>Không có ảnh.</span>
									            </c:otherwise>
									        </c:choose>
									    </td>
									    <td class="align-middle">${row.roomSize}</td>
									    
									    <!-- Fix the remaining columns -->
									    <td class="align-middle">${row.roomBedCount}</td>
									    <td class="align-middle">${row.roomStarCount}</td>
									    <td class="align-middle">${row.roomPricePerHourVnd}</td>
									    <td class="align-middle">${row.roomIsAvailable ? 'Có' : 'Không'}</td>
									    <td class="align-middle">${row.roomNote}</td>
									    
									    <td class="align-middle">
									    
									    <form action="${pageContext.request.contextPath}/room" method="post" style="display: inline;">
							                <input type="hidden" name="action" value="delete">
							                <input type="hidden" name="room_id" value="${row.roomId}">
							                <button type="submit" class="btn btn-danger btn-action">
							                    Xóa
							                </button>
							            </form>
							            
							            <button type="button" class="btn btn-warning" data-bs-toggle="modal" data-bs-target="#editPhongModal${row.roomId}">
											        Sửa
											    </button>

									        <!-- Modal Sửa Phòng -->
									        <div class="modal fade" id="editPhongModal${row.roomId}" tabindex="-1" aria-labelledby="editPhongModalLabel" aria-hidden="true">
									            <div class="modal-dialog">
									                <div class="modal-content">
									                    <div class="modal-header">
									                        <h5 class="modal-title" id="editPhongModalLabel">
									                            Sửa phòng có ID: ${row.roomId}
									                        </h5>
									                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
									                    </div>
									                    <div class="modal-body">
									                        <form action="${pageContext.request.contextPath}/room" method="post" enctype="multipart/form-data">
									                            <input type="hidden" name="action" value="update">
									                            <input type="hidden" name="room_id" value="${row.roomId}">
									                            
									                            <!-- Room fields for editing -->
									                            <div class="mb-3">
									                                <label for="room_name" class="form-label">Tên</label>
									                                <input type="text" class="form-control" id="room_name" name="room_name" value="${row.roomName}" required>
									                            </div>
									                            
									                            <div class="mb-3">
									                                <span class="form-switch me-${row.roomId}">
									                                    <input class="form-check-input" type="checkbox" id="is_update_room_image_${row.roomId}" name="is_update_room_image" onchange="toggleInput('is_update_room_image_${row.roomId}', 'roomImage_${row.roomId}')">
									                                </span>
									                                <label for="room_image_${row.roomId}" class="form-label">Ảnh minh hoạ</label>
									                                <input type="file" class="form-control" id="roomImage_${row.roomId}" name="room_image" disabled required />
									                            </div>
									                            
									                            <div class="mb-3">
									                                <label for="room_size" class="form-label"> Diện tích (m<sup>2</sup>)</label>
									                                <input type="number" class="form-control" id="room_size" name="room_size" value="${row.roomSize}" required>
									                            </div>
									                            
									                            <div class="mb-3">
									                                <label for="room_bed_count" class="form-label">Số giường</label>
									                                <select class="form-select" id="room_bed_count" name="room_bed_count" aria-label="Floating label select example">
									                                    <option value="1" ${row.roomBedCount == 1 ? "selected" : ""}>1 giường</option>
									                                    <option value="2" ${row.roomBedCount == 2 ? "selected" : ""}>2 giường</option>
									                                    <option value="3" ${row.roomBedCount == 3 ? "selected" : ""}>3 giường</option>
									                                    <option value="4" ${row.roomBedCount == 4 ? "selected" : ""}>4 giường</option>
									                                    <option value="5" ${row.roomBedCount == 5 ? "selected" : ""}>5 giường</option>
									                                </select>
									                            </div>
									                            
									                            <div class="mb-3">
									                                <label for="room_star_count" class="form-label">Số sao</label>
									                                <select class="form-select" id="room_star_count" name="room_star_count" aria-label="Floating label select example">
									                                    <option value="1" ${row.roomStarCount == 1 ? "selected" : ""}>1 sao</option>
									                                    <option value="2" ${row.roomStarCount == 2 ? "selected" : ""}>2 sao</option>
									                                    <option value="3" ${row.roomStarCount == 3 ? "selected" : ""}>3 sao</option>
									                                    <option value="4" ${row.roomStarCount == 4 ? "selected" : ""}>4 sao</option>
									                                    <option value="5" ${row.roomStarCount == 5 ? "selected" : ""}>5 sao</option>
									                                </select>
									                            </div>
									                            
									                            <div class="mb-3">
									                                <label for="room_price_per_hour_vnd" class="form-label">Giá tiền (₫/đêm)</label>
									                                <input type="number" class="form-control" id="room_price_per_hour_vnd" name="room_price_per_hour_vnd" value="${row.roomPricePerHourVnd}" required>
									                            </div>
									                            
									                            <div class="mb-3">
									                                <label for="room_is_available" class="form-label">Khả dụng</label>
									                                <select class="form-select" id="room_is_available" name="room_is_available" aria-label="Floating label select example">
									                                    <option value="1" ${row.roomIsAvailable ? "selected" : ""}>Có</option>
									                                    <option value="0" ${!row.roomIsAvailable ? "selected" : ""}>Không</option>
									                                </select>
									                            </div>
									                            
									                            <div class="mb-3">
									                                <label for="room_note" class="form-label">Mô tả</label>
									                                <textarea class="form-control" id="room_note" name="room_note" rows="3" required>${row.roomNote}</textarea>
									                            </div>
									                            <button type="submit" class="btn btn-warning">Sửa</button>
									                        </form>
									                    </div>
									                </div>
									            </div>
									        </div>
									    </td>
									</tr>
								</c:forEach>
  							</tbody>
						</table>

        				</div>
      				</div>
    			</div>
  			</div>
		</section>
	</main><!-- End #main -->
	
	<div class="modal fade" id="addPhongModal" tabindex="-1" aria-labelledby="addPhongModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="addPhongModalLabel">Thêm Phòng</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<form action="${pageContext.request.contextPath}/room" method="post" enctype="multipart/form-data">
						<input type="hidden" name="action" value="add">
		
						<div class="mb-3">
							<label for="room_name" class="form-label">Tên</label> 
							<input type="text" class="form-control" id="room_name" name="room_name" required>
						</div>
						
						<div class="mb-3">
							<label for="room_image" class="form-label">Ảnh minh hoạ</label> 
							<input type="file" class="form-control" id="room_image" name="room_image">
						</div>
						
						<div class="mb-3">
							<label for="room_size" class="form-label">	Diện tích (m<sup>2</sup>)</label> 
							<input type="number" class="form-control" id="room_size" name="room_size" required>
						</div>
						
						<div class="mb-3">
							<label for="room_bed_count" class="form-label">Số giường</label>
							<select class="form-select" id="room_bed_count" name="room_bed_count">
								<option value="1" selected>1 giường</option>
								<option value="2">2 giường</option>
								<option value="3">3 giường</option>
								<option value="4">4 giường</option>
								<option value="5">5 giường</option>
							</select>
		                </div>
						
						<div class="mb-3">
							<label for="room_star_count" class="form-label">Số sao</label>
		                 	<select class="form-select" id="room_star_count" name="room_star_count">
								<option value="1">1 sao</option>
								<option value="2">2 sao</option>
								<option value="3" selected>3 sao</option>
								<option value="4">4 sao</option>
								<option value="5">5 sao</option>
		                	</select>
		                </div>
		                   
						<div class="mb-3">
							<label for="room_price_per_hour_vnd" class="form-label">Giá tiền (₫/đêm)</label> 
							<input type="number" class="form-control" id="room_price_per_hour_vnd" name="room_price_per_hour_vnd" required>
						</div>
						
						<div class="mb-3">
							<label for="room_is_available" class="form-label">Khả dụng</label>
		                    <select class="form-select" id="room_is_available" name="room_is_available">
		                    	<option value="1" selected>Có</option>
		                        <option value="0">Không</option>
		                    </select>
		                </div>
						
						<div class="mb-3">
							<label for="room_note" class="form-label">Mô tả</label>
							<textarea class="form-control" id="room_note" name="room_note" rows="3" required></textarea>
						</div>
							
						<button type="submit" class="btn btn-success">Thêm</button>
					</form>
				</div>
			</div>
		</div>
	</div>
	
	<footer id="footer" class="footer">
	  <!-- <div class="copyright">
	    &copy; Copyright <strong><span>NiceAdmin</span></strong>. All Rights Reserved
	  </div>
	  <div class="credits">
	    Designed by <a href="https://bootstrapmade.com/">BootstrapMade</a>
	  </div> -->
	</footer>
	
	<script src="assets/vendor/apexcharts/apexcharts.min.js"></script>
	<script src="assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script src="assets/vendor/chart.js/chart.umd.js"></script>
	<script src="assets/vendor/echarts/echarts.min.js"></script>
	<script src="assets/vendor/quill/quill.js"></script>
	<script src="assets/vendor/simple-datatables/simple-datatables.js"></script>
	<script src="assets/vendor/tinymce/tinymce.min.js"></script>
	<script src="assets/vendor/php-email-form/validate.js"></script>
	<script src="assets/js/main.js"></script>

</body>

</html>