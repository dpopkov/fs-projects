import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { describe, expect, test } from 'vitest';
import { render, screen, waitFor } from '@testing-library/react';
import '@testing-library/jest-dom/vitest';
import Carlist from './components/Carlist';

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      retry: false,
    },
  },
});

const wrapper = ({ children }: { children: React.ReactNode }) => (
  <QueryClientProvider client={queryClient}>{children}</QueryClientProvider>
);

// ------------------------------------------
// For these tests you should run the backend
// ------------------------------------------
describe('Carlist tests', () => {
  test('component renders', () => {
    render(<Carlist />, { wrapper });
    expect(screen.getByText(/Loading/i)).toBeInTheDocument();
  });

  test('Cars are fetched', async () => {
    render(<Carlist />, { wrapper });

    await waitFor(() => screen.getByText(/New Car/i));
    // For this test the db should contain Toyota
    expect(screen.getByText(/Toyota/i)).toBeInTheDocument();
  });
});
