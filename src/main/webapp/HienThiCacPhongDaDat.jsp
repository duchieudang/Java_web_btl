<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page import="booking.BookingModel"%>
<%@ page import="objects.BookingObject"%>
<%@ page import="objects.RoomObject"%>
<%@ page import="room.RoomModel"%>
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

				<c:if test="${not empty errorMessage}">
					<div class="alert alert-warning text-center fs-5">${errorMessage}</div>
				</c:if>


				<!-- Kết quả phòng -->

				<div class="row g-4" id="room-data">
					<c:choose>
						<c:when test="${empty bookingList}">
							<div class="col-12 text-center">
								<p class="text-danger fs-4">Không có phòng nào phù hợp với
									yêu cầu của bạn.</p>
							</div>
						</c:when>
						<c:otherwise>
							<c:forEach var="booking" items="${bookingList}"
								varStatus="status">
								<c:set var="room" value="${roomList[status.index]}" />
								<div class="col-lg-4 col-md-6">
									<div class="room-item shadow rounded overflow-hidden border">
										<div class="position-relative">
											<img
												style="width: 100%; height: auto; aspect-ratio: 16/9; object-fit: cover;"
												src="data:image/jpeg;base64,${room.roomImageBase64}"
												alt="${room.roomName}"> <small
												class="position-absolute start-0 top-100 translate-middle-y bg-primary text-white rounded py-1 px-3 ms-4">
												${room.roomPricePerHourVnd}₫/đêm </small>
										</div>
										<div class="p-4">
											<h5 class="mb-2">${room.roomName}</h5>
											<p class="text-muted mb-2">${room.roomNote}</p>
											<p class="mb-2">
												<i class="fa fa-bed text-primary"></i> <small>
													${room.roomBedCount} giường</small>
											</p>

											<hr class="my-3">

											<div class="mb-2">
												<strong>Ngày bắt đầu:</strong> ${booking.bookingStartDate}
											</div>
											<div class="mb-2">
												<strong>Ngày kết thúc:</strong> ${booking.bookingEndDate}
											</div>
											<div class="mb-2">
												<strong>Số người:</strong> ${booking.bookingPeopleCount}
											</div>
											<div class="mb-2">
												<strong>Đặt lúc:</strong> ${booking.bookingCreatedAt}
											</div>
											<div class="mb-2">
												<strong>Liên hệ:</strong> ${booking.customerContact}
											</div>
											<div class="mb-2">
												<strong>CCCD:</strong> ${booking.customerContact1}
											</div>




											<c:choose>
												<c:when test="${booking.bookingState == 1}">
													<a class="btn btn-sm btn-warning mt-3 w-100"
														href="${pageContext.request.contextPath}/findbookingforclient?uuid=${booking.bookingUuid}">
														Đánh giá </a>
												</c:when>
												<c:when test="${booking.bookingState == 0}">
													<a class="btn btn-sm btn-danger mt-3 w-100" href="#">
														Chưa được chấp nhận </a>
												</c:when>
												<c:when test="${booking.bookingState == -1}">
													<a class="btn btn-sm btn-secondary mt-3 w-100" href="#">
														Đã bị từ chối </a>
												</c:when>
											</c:choose>




										</div>
									</div>
								</div>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</div>




				<!-- PHÂN TRANG -->
				<div class="pagination">
					<c:if test="${currentPage > 1}">
						<a
							href="phong-list-3?uuid=${uuid}&page=${currentPage - 1}&pageSize=${pageSize}">Trước</a>
					</c:if>

					<c:forEach var="i" begin="1" end="${totalPages}">
						<a href="phong-list-3?uuid=${uuid}&page=${i}&pageSize=${pageSize}"
							class="${i == currentPage ? 'active' : ''}"> ${i} </a>
					</c:forEach>

					<c:if test="${currentPage < totalPages}">
						<a
							href="phong-list-3?uuid=${uuid}&page=${currentPage + 1}&pageSize=${pageSize}">Tiếp</a>
					</c:if>
				</div>

			</div>
		</div>

		<%@ include file="fragments2/footer.jsp"%>
	</div>
	<%@ include file="fragments2/include-js.jsp"%>
</body>
</html>
