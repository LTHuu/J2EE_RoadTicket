
const API_BASE_URL = (
  import.meta.env.VITE_API_BASE_URL || "http://localhost:8080"
).replace(/\/+$/, "");

const AUTH_SESSION_KEY = "roadticket-auth";

async function request(path, options = {}) {
  let response;

  try {
    const storedSession = localStorage.getItem(AUTH_SESSION_KEY);
    const session = storedSession ? JSON.parse(storedSession) : null;

    response = await fetch(`${API_BASE_URL}${path}`, {
      ...options,
      headers: {
        "Content-Type": "application/json",
        ...(session?.accessToken
          ? { Authorization: `Bearer ${session.accessToken}` }
          : {}),
        ...options.headers,
      },
    });
  } catch (error) {
    if (error instanceof TypeError) {
      throw new Error("Không thể kết nối đến máy chủ. Vui lòng thử lại.");
    }
    throw error;
  }

  const responseText = await response.text();
  let body = null;

  if (responseText) {
    try {
      body = JSON.parse(responseText);
    } catch {
      body = { message: responseText };
    }
  }

  if (!response.ok) {
    throw new Error(
      body?.message ||
        body?.detail ||
        body?.error ||
        "Đã xảy ra lỗi. Vui lòng thử lại."
    );
  }

  return body;
}

export function getAllTickets() {
  return request("/api/tickets");
}

export function getTicketById(id) {
  return request(`/api/tickets/${id}`);
}

export function createTicket(ticketData) {
  return request("/api/tickets/create", {
    method: "POST",
    body: JSON.stringify(ticketData),
  });
}

export function updateTicket(id, ticketData) {
  return request(`/api/tickets/${id}`, {
    method: "PUT",
    body: JSON.stringify(ticketData),
  });
}

export function deleteTicket(id) {
  return request(`/api/tickets/${id}`, {
    method: "DELETE",
  });
}

export function updateTicketStatus(id, status) {
  return request(`/api/tickets/${id}/status`, {
    method: "PUT",
    body: JSON.stringify({ status }),
  });
}
