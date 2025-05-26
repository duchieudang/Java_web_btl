<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<title>Khách sạn Grand Palace</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<%@ include file="fragments2/include-css.jsp"%>
<style>
/* Bộ lọc */
.filter-form {
	margin: 20px auto;
	padding: 15px;
	background-color: #f8f9fa;
	border-radius: 10px;
	max-width: 1000px;
}

.filter-form .input-group-text {
	background-color: #e9ecef;
	font-weight: 500;
}

.filter-form .btn-primary {
	background-color: #007bff;
	border: none;
	transition: 0.3s;
}

.filter-form .btn-primary:hover {
	background-color: #0056b3;
}

table {
	width: 80%;
	margin: 20px auto;
	border-collapse: collapse;
}

table, th, td {
	border: 1px solid #ddd;
	text-align: center;
}

th {
	background-color: #f4f4f4;
	padding: 10px;
}

.pagination {
	margin: 20px auto;
	text-align: center;
}

.pagination a {
	margin: 0 5px;
	text-decoration: none;
	padding: 5px 10px;
	background-color: #007BFF;
	color: white;
	border-radius: 5px;
	transition: 0.3s;
}

.pagination a:hover, .pagination a.active {
	background-color: #0056b3;
}
</style>
</head>

<body>
	<div class="container-xxl bg-white p-0">
		<%@ include file="fragments2/spinner.jsp"%>
		<%@ include file="fragments2/header.jsp"%>

		<div class="container-xxl py-5" id="rooms">
			<div class="container">
				<!-- Tạo tham số cho bộ lọc -->
				<c:set var="paramString" value="" />
				<c:if test="${not empty param.giuong}">
					<c:set var="paramString"
						value="${paramString}giuong=${param.giuong}&" />
				</c:if>
				<c:if test="${not empty param.gia}">
					<c:set var="paramString" value="${paramString}gia=${param.gia}&" />
				</c:if>
				<c:if test="${not empty param.ngayBatDau}">
					<c:set var="paramString"
						value="${paramString}ngayBatDau=${param.ngayBatDau}&" />
				</c:if>
				<c:if test="${not empty param.ngayKetThuc}">
					<c:set var="paramString"
						value="${paramString}ngayKetThuc=${param.ngayKetThuc}&" />
				</c:if>
				<c:if test="${not empty errorMessage}">
					<div id="error-message"
						class="alert alert-danger text-center alert-dismissible fade show"
						role="alert">
						<strong>Lỗi: </strong> ${errorMessage}
			
					</div>

					<script type="text/javascript">
						// Tự động tắt thông báo sau 3 giây
					setTimeout(function() {
							closeErrorMessage();
					}, 2000);

						// Hàm tắt thông báo khi nhấn nút đóng
						function closeErrorMessage() {
							var errorMessage = document
									.getElementById("error-message");
							if (errorMessage) {
								errorMessage.style.display = "none";
							}
						}
					</script>
				</c:if>


				<!-- Form Lọc -->
				<form action="phong-list" method="get" class="filter-form shadow-sm">
					<div class="row g-3">
						<div class="col-md-3">
							<div class="input-group">
								<span class="input-group-text"><i class="fa fa-bed"></i></span>
								<input type="number" class="form-control" name="giuong"
									id="giuong" min="1" placeholder="Giường tối thiểu"
									value="${param.giuong}">
							</div>
						</div>

						<div class="col-md-3">
							<div class="input-group">
								<span class="input-group-text"><i
									class="fa fa-money-bill-wave"></i></span> <input type="number"
									class="form-control" name="gia" id="gia" min="0"
									placeholder="Giá tối đa" value="${param.gia}">
							</div>
						</div>

						<div class="col-md-3">
							<div class="input-group">
								<span class="input-group-text"><i
									class="fa fa-calendar-day"></i></span> <input type="date"
									class="form-control" name="ngayBatDau" id="ngayBatDau"
									value="${param.ngayBatDau}">
							</div>
						</div>

						<div class="col-md-3">
							<div class="input-group">
								<span class="input-group-text"><i
									class="fa fa-calendar-check"></i></span> <input type="date"
									class="form-control" name="ngayKetThuc" id="ngayKetThuc"
									value="${param.ngayKetThuc}">
							</div>
						</div>

						<div class="col-12 text-center">
							<button type="submit" class="btn btn-primary px-4">
								<i class="fa fa-filter me-2"></i>Lọc kết quả
							</button>
						</div>
					</div>
				</form>

				<!-- Kết quả phòng -->
				<div class="row g-4" id="room-data">
					<c:choose>
						<c:when test="${empty roomList}">
							<!-- Hiển thị thông báo khi không có phòng -->
							<div class="col-12 text-center">
								<p class="text-danger fs-4">Không có phòng nào phù hợp với
									yêu cầu của bạn.</p>
							</div>
						</c:when>
						<c:otherwise>
							<!-- Lặp qua danh sách phòng nếu có -->
							<c:forEach var="row" items="${roomList}">
								<div class="col-lg-4 col-md-6">
									<div class="room-item shadow rounded overflow-hidden">
										<div class="position-relative">
											<img
												style="width: 100%; height: auto; aspect-ratio: 16/9; object-fit: cover;"
												src="data:image/jpeg;base64,${row.roomImageBase64}"
												alt="${row.roomName}"> <small
												class="position-absolute start-0 top-100 translate-middle-y bg-primary text-white rounded py-1 px-3 ms-4">
												${row.roomPricePerHourVnd}₫/đêm </small>
										</div>
										<div class="p-4">
											<h5>${row.roomName}</h5>
											<p class="text-body">${row.roomNote}</p>
											<div>
												<small><i class="fa fa-bed text-primary"></i>
													${row.roomBedCount} giường</small>
											</div>
											<a class="btn btn-sm btn-dark mt-3"
												href="${pageContext.request.contextPath}/bookingforclient?room_id=${row.roomId}">Đặt
												phòng</a>
										</div>
									</div>
								</div>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</div>

				<!-- Phân trang -->
				<div class="pagination">
					<c:if test="${currentPage > 1}">
						<a href="phong-list?page=${currentPage - 1}&${paramString}">Previous</a>
					</c:if>
					<a href="#" class="active">Page ${currentPage}</a>
					<c:if test="${!isLastPage}">
						<a href="phong-list?page=${currentPage + 1}&${paramString}">Next</a>
					</c:if>
				</div>
			</div>
		</div>

		<%@ include file="fragments2/footer.jsp"%>
	</div>
	<%@ include file="fragments2/include-js.jsp"%>
</body>
</html>
